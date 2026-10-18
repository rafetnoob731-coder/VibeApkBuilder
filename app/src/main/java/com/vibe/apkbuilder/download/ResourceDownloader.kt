package com.vibe.apkbuilder.download

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Auto-download of required SDK / toolchain pieces.
 * On phone: only lightweight caches and templates.
 * Full JDK/Gradle/SDK installs require a remote worker or desktop companion.
 */
data class ResourceSpec(
    val id: String,
    val name: String,
    val version: String,
    val sizeMb: Int,
    val required: Boolean
)

sealed class DownloadEvent {
    data class Progress(val id: String, val percent: Int) : DownloadEvent()
    data class Completed(val id: String, val path: String) : DownloadEvent()
    data class Failed(val id: String, val reason: String) : DownloadEvent()
    data class Skipped(val id: String, val reason: String) : DownloadEvent()
}

class ResourceDownloader {

    private val catalog = listOf(
        ResourceSpec("jdk17", "JDK 17", "17.0.12", 180, true),
        ResourceSpec("gradle89", "Gradle", "8.9", 120, true),
        ResourceSpec("sdk34", "Android SDK Platform 34", "34", 90, true),
        ResourceSpec("bt34", "Build Tools", "34.0.0", 45, true),
        ResourceSpec("webview-template", "WebView Project Template", "1.0", 2, false),
        ResourceSpec("pwa-shell", "PWA Shell Template", "1.0", 3, false)
    )

    fun requiredFor(projectType: String): List<ResourceSpec> {
        return when (projectType.lowercase()) {
            "android", "kotlin", "java" -> catalog.filter { it.required }
            "web", "html", "pwa" -> catalog.filter { it.id.contains("template") || it.id.contains("pwa") }
            else -> catalog.filter { it.required }
        }
    }

    /**
     * Simulated progressive download — replace body with real OkHttp + verified checksums.
     * Phone cannot install system JDK; mark as remote-worker requirement.
     */
    fun downloadAll(specs: List<ResourceSpec>, phoneMode: Boolean = true): Flow<DownloadEvent> = flow {
        specs.forEach { spec ->
            if (phoneMode && (spec.id.startsWith("jdk") || spec.id.startsWith("gradle") || spec.id.startsWith("sdk") || spec.id.startsWith("bt"))) {
                emit(DownloadEvent.Skipped(spec.id, "Requires remote build worker on phone"))
                return@forEach
            }
            for (p in 0..100 step 10) {
                emit(DownloadEvent.Progress(spec.id, p))
                delay(80)
            }
            emit(DownloadEvent.Completed(spec.id, "/data/vibe/cache/${spec.id}-${spec.version}"))
        }
    }
}
