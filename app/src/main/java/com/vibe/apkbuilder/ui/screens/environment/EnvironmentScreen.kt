package com.vibe.apkbuilder.ui.screens.environment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.components.VibeCard
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Success
import com.vibe.apkbuilder.ui.theme.SurfaceVariant
import com.vibe.apkbuilder.ui.theme.Warning

@Composable
fun EnvironmentScreen(
    projectId: String,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val items = listOf(
        EnvItem("JDK 17", "17.0.12", "Installed", true, 1f),
        EnvItem("Gradle", "8.9", "Installed", true, 1f),
        EnvItem("Android SDK", "34", "Installing…", false, 0.65f),
        EnvItem("Build Tools", "34.0.0", "Installing…", false, 0.4f),
        EnvItem("Node.js", "18.19.0", "Installed", true, 1f),
        EnvItem("npm", "9.8.1", "Installed", true, 1f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = OnBackground)
            }
            Text("Environment Setup", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("Required Components", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            items.forEach { item ->
                EnvRow(item)
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.height(12.dp))
            VibeCard(modifier = Modifier.fillMaxWidth()) {
                Text("Installation Log", color = OnSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                Text("Downloading platform-34…", color = OnSurfaceVariant, fontSize = 12.sp)
                Text("Extracting build-tools…", color = OnSurfaceVariant, fontSize = 12.sp)
            }
            Spacer(Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(20.dp)) {
            GradientButton(text = "Next", onClick = onNext, modifier = Modifier.fillMaxWidth())
        }
    }
}

private data class EnvItem(
    val name: String,
    val version: String,
    val status: String,
    val installed: Boolean,
    val progress: Float
)

@Composable
private fun EnvRow(item: EnvItem) {
    VibeCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (item.installed) Success.copy(alpha = 0.15f) else Warning.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (item.installed) Icons.Default.CheckCircle else Icons.Default.Download,
                    null,
                    tint = if (item.installed) Success else Warning,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(item.version, color = OnSurfaceVariant, fontSize = 12.sp)
                if (!item.installed) {
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { item.progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = Primary,
                        trackColor = Color(0xFF2A2A3C)
                    )
                }
            }
            Text(
                item.status,
                color = if (item.installed) Success else Warning,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
