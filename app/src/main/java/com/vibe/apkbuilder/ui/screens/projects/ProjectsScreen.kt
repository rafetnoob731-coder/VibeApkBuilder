package com.vibe.apkbuilder.ui.screens.projects

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibe.apkbuilder.data.model.Project
import com.vibe.apkbuilder.data.model.ProjectType
import com.vibe.apkbuilder.ui.components.BottomNavBar
import com.vibe.apkbuilder.ui.components.ProjectListItem
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Outline
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant


@Composable
fun ProjectsScreen(
    onBack: () -> Unit,
    onProjectClick: (String) -> Unit,
    onImport: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }

    val sampleProjects = remember {
        listOf(
            Project(id = "1", name = "GameApp", type = ProjectType.ANDROID_KOTLIN, language = "Kotlin", lastModified = System.currentTimeMillis() - 2 * 86400000),
            Project(id = "2", name = "WebsiteApp", type = ProjectType.WEB_HTML, language = "HTML", isConverted = true, lastModified = System.currentTimeMillis() - 3 * 86400000),
            Project(id = "3", name = "MyProject", type = ProjectType.ANDROID_JAVA, language = "Java", lastModified = System.currentTimeMillis() - 5 * 86400000),
            Project(id = "4", name = "FoodDelivery", type = ProjectType.ANDROID_KOTLIN, language = "Kotlin", framework = "Compose", lastModified = System.currentTimeMillis() - 7 * 86400000),
            Project(id = "5", name = "E-Commerce", type = ProjectType.WEB_REACT, language = "TypeScript", lastModified = System.currentTimeMillis() - 10 * 86400000),
            Project(id = "6", name = "SocialApp", type = ProjectType.ANDROID_KOTLIN, language = "Kotlin", lastModified = System.currentTimeMillis() - 14 * 86400000)
        )
    }

    val filtered = sampleProjects.filter {
        (filter == "All" ||
                (filter == "Android" && it.type.name.startsWith("ANDROID")) ||
                (filter == "Web" && it.type.name.startsWith("WEB")) ||
                (filter == "Converted" && it.isConverted)) &&
                (query.isBlank() || it.name.contains(query, ignoreCase = true))
    }

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
                "Projects",
                color = OnBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search projects…", color = OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = OnSurfaceVariant) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Outline,
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedTextColor = OnBackground,
                unfocusedTextColor = OnBackground,
                cursorColor = Primary
            )
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Android", "Web", "Converted").forEach { label ->
                val selected = filter == label
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) Primary else SurfaceVariant)
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                        .then(Modifier.clickable { filter = label }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        label,
                        color = if (selected) Color.White else OnSurfaceVariant,
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered, key = { it.id }) { project ->
                    val days = ((System.currentTimeMillis() - project.lastModified) / 86400000).toInt()
                    val timeLabel = when {
                        days == 0 -> "Today"
                        days == 1 -> "1 day ago"
                        else -> "$days days ago"
                    }
                    ProjectListItem(
                        name = project.name,
                        meta = "${project.language} • $timeLabel",
                        status = if (project.isConverted) "Converted" else null,
                        onClick = { onProjectClick(project.id) }
                    )
                }
                item { Spacer(Modifier.height(80.dp)) }
            }

            FloatingActionButton(
                onClick = onImport,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                containerColor = Primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Import")
            }
        }

        BottomNavBar(selected = "projects") { }
    }
}
