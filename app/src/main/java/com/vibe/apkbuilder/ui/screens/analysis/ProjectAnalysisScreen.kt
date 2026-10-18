package com.vibe.apkbuilder.ui.screens.analysis

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.components.VibeCard
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.Error
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Success
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant
import com.vibe.apkbuilder.ui.theme.Warning

@Composable
fun ProjectAnalysisScreen(
    projectId: String,
    projectName: String = "MyApp",
    projectMeta: String = "Android · Kotlin · Gradle",
    healthScore: Int = 85,
    onBack: () -> Unit,
    onViewDetails: () -> Unit
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
            Text(
                "Project Analysis",
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
            VibeCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", color = Primary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(projectName, color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Text(projectMeta, color = OnSurfaceVariant, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Health ring
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                        Canvas(modifier = Modifier.size(120.dp)) {
                            val stroke = 12.dp.toPx()
                            drawArc(
                                color = Color(0xFF2A2A3C),
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = stroke, cap = StrokeCap.Round),
                                size = Size(size.width, size.height)
                            )
                            drawArc(
                                color = if (healthScore >= 80) Success else if (healthScore >= 50) Warning else Error,
                                startAngle = -90f,
                                sweepAngle = 360f * (healthScore / 100f),
                                useCenter = false,
                                style = Stroke(width = stroke, cap = StrokeCap.Round),
                                size = Size(size.width, size.height)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$healthScore%", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                            Text("Good", color = Success, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Project Health", color = OnSurfaceVariant, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Findings", color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))

            FindingRow("Build Issues", "0", Success, Icons.Default.CheckCircle)
            FindingRow("Dependencies", "2 warnings", Warning, Icons.Default.Warning)
            FindingRow("Permissions", "1 warning", Warning, Icons.Default.Warning)
            FindingRow("Security", "0", Success, Icons.Default.CheckCircle)
            FindingRow("Performance", "1 suggestion", Color(0xFF40C4FF), Icons.Default.Info)

            Spacer(Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(20.dp)) {
            GradientButton(
                text = "View Details",
                onClick = onViewDetails,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FindingRow(
    label: String,
    value: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(label, color = OnBackground, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(value, color = color, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}
