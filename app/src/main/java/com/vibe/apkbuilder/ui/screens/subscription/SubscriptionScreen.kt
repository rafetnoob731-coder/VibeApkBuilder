package com.vibe.apkbuilder.ui.screens.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.vibe.apkbuilder.ui.components.GradientButton
import com.vibe.apkbuilder.ui.theme.Background
import com.vibe.apkbuilder.ui.theme.GradientEnd
import com.vibe.apkbuilder.ui.theme.GradientStart
import com.vibe.apkbuilder.ui.theme.OnBackground
import com.vibe.apkbuilder.ui.theme.OnSurfaceVariant
import com.vibe.apkbuilder.ui.theme.Outline
import com.vibe.apkbuilder.ui.theme.Primary
import com.vibe.apkbuilder.ui.theme.Success
import com.vibe.apkbuilder.ui.theme.Surface
import com.vibe.apkbuilder.ui.theme.SurfaceVariant

@Composable
fun SubscriptionScreen(
    onBack: () -> Unit,
    onUpgrade: (String) -> Unit
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
            Text("VIBE Pro", color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text("Unlock advanced features", color = OnSurfaceVariant, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceVariant)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Primary)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Monthly", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Yearly Save 20%", color = OnSurfaceVariant, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            PlanCard(
                name = "Free",
                price = "$0 / month",
                features = listOf("Basic conversion", "Limited AI features", "Basic support"),
                current = true,
                onSelect = { }
            )
            Spacer(Modifier.height(12.dp))
            PlanCard(
                name = "Pro",
                price = "$19.99 / month",
                features = listOf("Full features", "Advanced AI", "Priority support"),
                highlighted = true,
                onSelect = { onUpgrade("pro") }
            )
            Spacer(Modifier.height(12.dp))
            PlanCard(
                name = "Universe",
                price = "$39.99 / month",
                features = listOf("Team features", "Larger projects", "Advanced automation"),
                onSelect = { onUpgrade("universe") }
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PlanCard(
    name: String,
    price: String,
    features: List<String>,
    current: Boolean = false,
    highlighted: Boolean = false,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .then(
                if (highlighted)
                    Modifier.background(Brush.horizontalGradient(listOf(GradientStart.copy(alpha = 0.2f), GradientEnd.copy(alpha = 0.15f))))
                else
                    Modifier.background(Surface)
            )
            .border(
                width = if (highlighted) 1.5.dp else 1.dp,
                color = if (highlighted) Primary else Outline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(18.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(name, color = OnBackground, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                if (current) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Success.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Current", color = Success, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(price, color = if (highlighted) Primary else OnSurfaceVariant, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            features.forEach { f ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                    Icon(Icons.Default.Check, null, tint = if (highlighted) Primary else Success, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(f, color = OnSurfaceVariant, fontSize = 13.sp)
                }
            }
            if (!current) {
                Spacer(Modifier.height(14.dp))
                GradientButton(
                    text = "Upgrade",
                    onClick = onSelect,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
