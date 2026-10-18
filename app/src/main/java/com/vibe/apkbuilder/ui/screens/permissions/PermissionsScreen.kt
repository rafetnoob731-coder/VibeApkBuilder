package com.vibe.apkbuilder.ui.screens.permissions

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.components.VibeCard
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.SurfaceVariant

@Composable
fun PermissionsScreen(
    projectId: String,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var internet by remember { mutableStateOf(true) }
    var camera by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf(false) }
    var mic by remember { mutableStateOf(false) }
    var storage by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }
    var bluetooth by remember { mutableStateOf(false) }

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
            Text("Permissions Manager", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("App Permissions", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text("Only enable what the app actually needs", color = OnSurfaceVariant, fontSize = 12.sp)
            Spacer(Modifier.height(16.dp))

            PermRow(Icons.Default.Wifi, "Internet", "Access to network resources", internet) { internet = it }
            PermRow(Icons.Default.CameraAlt, "Camera", "Take photos and videos", camera) { camera = it }
            PermRow(Icons.Default.LocationOn, "Location", "Access device location", location) { location = it }
            PermRow(Icons.Default.Mic, "Microphone", "Record audio", mic) { mic = it }
            PermRow(Icons.Default.Folder, "Storage", "Read and write files", storage) { storage = it }
            PermRow(Icons.Default.Notifications, "Notifications", "Show notifications", notifications) { notifications = it }
            PermRow(Icons.Default.Bluetooth, "Bluetooth", "Connect to Bluetooth devices", bluetooth) { bluetooth = it }

            Spacer(Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(20.dp)) {
            GradientButton(text = "Save Changes", onClick = onSave, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun PermRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    VibeCard(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = Primary, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(subtitle, color = OnSurfaceVariant, fontSize = 12.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onChecked,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = androidx.compose.ui.graphics.Color.White,
                    checkedTrackColor = Primary,
                    uncheckedThumbColor = OnSurfaceVariant,
                    uncheckedTrackColor = SurfaceVariant
                )
            )
        }
    }
}
