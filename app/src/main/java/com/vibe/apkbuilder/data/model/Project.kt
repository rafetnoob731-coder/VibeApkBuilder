package com.vibe.apkbuilder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

enum class ProjectType {
    ANDROID_JAVA,
    ANDROID_KOTLIN,
    WEB_HTML,
    WEB_REACT,
    WEB_VUE,
    WEB_ANGULAR,
    PWA,
    UNKNOWN
}

enum class BuildSystem {
    GRADLE,
    GRADLE_KTS,
    MAVEN,
    NPM,
    NONE
}

enum class ProjectHealth {
    GOOD,
    WARNING,
    CRITICAL,
    UNKNOWN
}

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val packageId: String = "com.example.app",
    val type: ProjectType = ProjectType.UNKNOWN,
    val buildSystem: BuildSystem = BuildSystem.NONE,
    val language: String = "Unknown",
    val framework: String? = null,
    val versionName: String = "1.0.0",
    val versionCode: Int = 1,
    val minSdk: Int = 24,
    val targetSdk: Int = 35,
    val compileSdk: Int = 35,
    val path: String = "",
    val health: ProjectHealth = ProjectHealth.UNKNOWN,
    val healthScore: Int = 0,
    val lastModified: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isConverted: Boolean = false,
    val sourceUrl: String? = null,
    val iconPath: String? = null
)

data class ProjectInventory(
    val projectId: String,
    val projectType: ProjectType,
    val mainLanguage: String,
    val framework: String?,
    val buildSystem: BuildSystem,
    val entryPoint: String?,
    val modules: List<String>,
    val sourceDirs: List<String>,
    val resourceDirs: List<String>,
    val dependencies: List<String>,
    val sdkRequirements: List<String>,
    val buildVariants: List<String>,
    val packageIdentifier: String?,
    val permissions: List<String>,
    val securityFindings: List<String>
)

data class DiagnosticFinding(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String,
    val severity: Severity,
    val category: String,
    val description: String,
    val evidence: String,
    val filePath: String? = null,
    val line: Int? = null,
    val rootCause: String,
    val recommendedFix: String,
    val verificationMethod: String
)

enum class Severity {
    ERROR, WARNING, INFO, SECURITY
}
