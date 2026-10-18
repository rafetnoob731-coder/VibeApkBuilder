package com.vibe.apkbuilder.ui.screens.rejection

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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.vibe.apkbuilder.ui.theme.Error
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant

@Composable
fun RejectionAnalyzerScreen(
    projectId: String,
    onBack: () -> Unit,
    onApplyFix: () -> Unit,
    onNewAnalysis: () -> Unit
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
            Text("Rejection Analyzer", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Status card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Error.copy(alpha = 0.12f))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Cancel, null, tint = Error, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Rejected", color = Error, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Google Play review decision", color = OnSurfaceVariant, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            VibeCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text("Reason", color = OnSurfaceVariant, fontSize = 12.sp)
                    Text("Violation of Play Policy", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("Category", color = OnSurfaceVariant, fontSize = 12.sp)
                    Text("Sensitive Permissions", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("AI Analysis", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            VibeCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "The app requests unnecessary permissions that do not match the declared functionality.",
                    color = OnBackground,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Suggested Fix", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            VibeCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Remove the unnecessary permission and update the related code. Rebuild, test, then resubmit.",
                    color = OnBackground,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceVariant)
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("View Changes", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            GradientButton(
                text = "Apply Fix",
                onClick = onApplyFix,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
