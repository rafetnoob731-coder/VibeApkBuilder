package com.vibe.apkbuilder.ui.screens.auth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.GradientEnd
import com.vibe.apkbuilder.ui.theme.GradientStart
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Outline
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Surface

@Composable
fun SignInScreen(
    onBack: () -> Unit,
    onSignIn: (email: String, password: String) -> Unit,
    onGoSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Sign In",
        onBack = onBack
    ) {
        AuthField("Email", email, KeyboardType.Email) { email = it }
        Spacer(Modifier.height(12.dp))
        AuthField("Password", password, KeyboardType.Password, isPassword = true) { password = it }
        Spacer(Modifier.height(24.dp))
        GradientButton(
            text = "Sign In",
            onClick = { onSignIn(email.trim(), password) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        TextButton(onClick = onGoSignUp, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Create account", color = Primary)
        }
    }
}

@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onSignUp: (name: String, email: String, password: String) -> Unit,
    onGoSignIn: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthScaffold(
        title = "Sign Up",
        onBack = onBack
    ) {
        AuthField("Display name", name, KeyboardType.Text) { name = it }
        Spacer(Modifier.height(12.dp))
        AuthField("Email", email, KeyboardType.Email) { email = it }
        Spacer(Modifier.height(12.dp))
        AuthField("Password", password, KeyboardType.Password, isPassword = true) { password = it }
        Spacer(Modifier.height(8.dp))
        Text(
            "Min 8 characters. Password is hashed on-device (PBKDF2).",
            color = OnSurfaceVariant,
            fontSize = 11.sp
        )
        Spacer(Modifier.height(24.dp))
        GradientButton(
            text = "Create Account",
            onClick = { onSignUp(name.trim(), email.trim(), password) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        TextButton(onClick = onGoSignIn, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Already have an account", color = Primary)
        }
    }
}

@Composable
private fun AuthScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit
) {
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
            Text(title, color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 36.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text("VIBE APK BUILDER", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(28.dp))
            content()
        }
    }
}

@Composable
private fun AuthField(
    label: String,
    value: String,
    keyboard: KeyboardType,
    isPassword: Boolean = false,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
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
