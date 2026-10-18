package com.vibe.apkbuilder.ui.screens.importproject

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Outline
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Secondary
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant

@Composable
fun ImportProjectScreen(
    onBack: () -> Unit,
    onImported: (String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf<String?>(null) }

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
            Text(
                "Import Project",
                color = OnBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                "Choose Import Method",
                color = OnBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(16.dp))

            ImportMethodCard(
                title = "From Device",
                subtitle = "Folder / ZIP / Files",
                icon = Icons.Default.Folder,
                selected = selectedMethod == "device",
                onClick = { selectedMethod = "device" }
            )
            Spacer(Modifier.height(12.dp))
            ImportMethodCard(
                title = "From Git",
                subtitle = "Repository URL",
                icon = Icons.Default.Link,
                selected = selectedMethod == "git",
                onClick = { selectedMethod = "git" }
            )
            Spacer(Modifier.height(12.dp))
            ImportMethodCard(
                title = "From URL",
                subtitle = "Website / HTML",
                icon = Icons.Default.Language,
                selected = selectedMethod == "url",
                onClick = { selectedMethod = "url" }
            )
            Spacer(Modifier.height(12.dp))
            ImportMethodCard(
                title = "Existing APK",
                subtitle = "Analyze & Convert",
                icon = Icons.Default.Android,
                selected = selectedMethod == "apk",
                onClick = { selectedMethod = "apk" }
            )

            Spacer(Modifier.height(28.dp))

            Text(
                "Supported Types",
                color = OnBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Android", "Java", "HTML", "CSS", "JS", "Vue", "Angular", "More…").forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceVariant)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(tag, color = OnSurfaceVariant, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            GradientButton(
                text = "Next",
                onClick = {
                    // Phase-1: create a demo project id
                    onImported("imported-${System.currentTimeMillis()}")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ImportMethodCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Primary.copy(alpha = 0.12f) else Surface)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) Primary else Outline,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected) Primary.copy(alpha = 0.2f) else SurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = if (selected) Primary else Secondary, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(subtitle, color = OnSurfaceVariant, fontSize = 12.sp)
            }
        }
    }
}
