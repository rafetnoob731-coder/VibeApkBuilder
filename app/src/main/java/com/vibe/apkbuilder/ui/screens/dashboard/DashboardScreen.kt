package com.vibe.apkbuilder.ui.screens.dashboard

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.ui.components.BottomNavBar
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.components.ProjectListItem
import com.vibe.apkbuilder.ui.components.QuickActionCard
import com.vibe.apkbuilder.ui.components.SectionHeader
import com.vibe.apkbuilder.ui.components.VibeCard
import com.vibe.apkbuilder.ui.navigation.Route
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.GradientEnd
import com.vibe.apkbuilder.ui.theme.GradientStart
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Secondary
import com.vibe.apkbuilder.ui.theme.Success
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    onProjectClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "VIBE APK BUILDER",
                    color = OnBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    "AI App Converter & Play Store Assistant",
                    color = OnSurfaceVariant,
                    fontSize = 11.sp
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Primary.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text("Pro Active", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Notifications, null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Hero banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF1A1240), Color(0xFF0D2A3A))
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        "Build • Convert • Fix • Publish",
                        color = OnBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        "Apps with AI Assistance",
                        color = OnSurfaceVariant,
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    GradientButton(
                        text = "+ New Project",
                        onClick = { onNavigate(Route.ImportProject.path) },
                        icon = Icons.Default.Add
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionHeader(title = "Quick Tools", action = "View All") { }

            Spacer(Modifier.height(12.dp))

            QuickActionCard(
                title = "Import Project",
                subtitle = "ZIP, Folder, Git, APK",
                icon = Icons.Default.Folder,
                iconTint = Primary,
                onClick = { onNavigate(Route.ImportProject.path) }
            )
            Spacer(Modifier.height(10.dp))
            QuickActionCard(
                title = "Web to App",
                subtitle = "Website to Android",
                icon = Icons.Default.Language,
                iconTint = Secondary,
                onClick = { onNavigate(Route.WebToApp.path) }
            )
            Spacer(Modifier.height(10.dp))
            QuickActionCard(
                title = "AI Project Doctor",
                subtitle = "Scan & Fix Issues",
                icon = Icons.Default.Code,
                iconTint = Color(0xFFB388FF),
                onClick = { onNavigate(Route.AiAssistant.create("demo")) }
            )
            Spacer(Modifier.height(10.dp))
            QuickActionCard(
                title = "Play Store Assistant",
                subtitle = "Prepare & Publish",
                icon = Icons.Default.Store,
                iconTint = Color(0xFF00E676),
                onClick = { onNavigate(Route.PlayStore.create("demo")) }
            )

            Spacer(Modifier.height(24.dp))

            SectionHeader(title = "Main Features", action = "View All") { }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureTile(
                    title = "Code Editor",
                    subtitle = "Edit & AI Assist",
                    icon = Icons.Default.Code,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Route.AiAssistant.create("demo")) }
                )
                FeatureTile(
                    title = "Build Center",
                    subtitle = "APK / AAB",
                    icon = Icons.Default.Build,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(Route.BuildCenter.create("demo")) }
                )
            }

            Spacer(Modifier.height(24.dp))

            SectionHeader(title = "Recent Projects", action = "View All") {
                onNavigate(Route.Projects.path)
            }

            Spacer(Modifier.height(12.dp))

            ProjectListItem(
                name = "MyGame App",
                meta = "Android • Gradle",
                status = "Build Success",
                statusColor = Success,
                onClick = { onProjectClick("demo-game") }
            )
            Spacer(Modifier.height(10.dp))
            ProjectListItem(
                name = "WebsiteApp",
                meta = "Web → Android • 3 days ago",
                status = null,
                onClick = { onProjectClick("demo-web") }
            )
            Spacer(Modifier.height(10.dp))
            ProjectListItem(
                name = "MyProject",
                meta = "Java • 5 days ago",
                status = null,
                onClick = { onProjectClick("demo-java") }
            )

            Spacer(Modifier.height(24.dp))
        }

        BottomNavBar(selected = "dashboard") { route ->
            when (route) {
                "projects" -> onNavigate(Route.Projects.path)
                "build" -> onNavigate(Route.BuildCenter.create("demo"))
                "ai" -> onNavigate(Route.AiAssistant.create("demo"))
                "profile" -> onNavigate(Route.Settings.path)
                else -> {}
            }
        }
    }
}

@Composable
private fun FeatureTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    VibeCard(modifier = modifier, onClick = onClick) {
        Column {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = Primary, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(title, color = OnBackground, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, color = OnSurfaceVariant, fontSize = 11.sp)
        }
    }
}
