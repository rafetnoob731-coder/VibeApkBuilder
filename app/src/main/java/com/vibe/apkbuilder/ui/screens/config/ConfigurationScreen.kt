package com.vibe.apkbuilder.ui.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Outline
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Surface

@Composable
fun ConfigurationScreen(
    projectId: String,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var appName by remember { mutableStateOf("My Application") }
    var packageId by remember { mutableStateOf("com.example.myapp") }
    var versionName by remember { mutableStateOf("1.0.0") }
    var versionCode by remember { mutableStateOf("1") }
    var minSdk by remember { mutableStateOf("24") }
    var targetSdk by remember { mutableStateOf("35") }
    var compileSdk by remember { mutableStateOf("35") }

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
            Text("Configuration", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("Application Information", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            ConfigField("App Name", appName) { appName = it }
            Spacer(Modifier.height(10.dp))
            ConfigField("Package ID", packageId) { packageId = it }
            Spacer(Modifier.height(10.dp))
            ConfigField("Version Name", versionName) { versionName = it }
            Spacer(Modifier.height(10.dp))
            ConfigField("Version Code", versionCode) { versionCode = it }

            Spacer(Modifier.height(20.dp))
            Text("SDK Configuration", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            ConfigField("Min SDK", minSdk) { minSdk = it }
            Spacer(Modifier.height(10.dp))
            ConfigField("Target SDK", targetSdk) { targetSdk = it }
            Spacer(Modifier.height(10.dp))
            ConfigField("Compile SDK", compileSdk) { compileSdk = it }
            Spacer(Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(20.dp)) {
            GradientButton(text = "Next", onClick = onNext, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ConfigField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = Outline,
            focusedContainerColor = Surface,
            unfocusedContainerColor = Surface,
            focusedTextColor = OnBackground,
            unfocusedTextColor = OnBackground,
            cursorColor = Primary,
            focusedLabelColor = Primary,
            unfocusedLabelColor = OnSurfaceVariant
        )
    )
}
