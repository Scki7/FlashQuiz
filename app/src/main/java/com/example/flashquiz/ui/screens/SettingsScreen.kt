package com.example.flashquiz.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashquiz.ui.theme.FlashQuizTheme

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = if (isDarkTheme) Color(0xFFBBA6FF) else Color(0xFF5140AF)
    val background = if (isDarkTheme) Color(0xFF1D1B29) else Color(0xFFFAF9FF)
    val cardBackground = if (isDarkTheme) Color(0xFF302D40) else Color(0xFFF7F5FF)
    val mainText = if (isDarkTheme) Color.White else Color(0xFF17151F)
    val secondaryText = if (isDarkTheme) Color(0xFFCAC5D9) else Color(0xFF686477)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {

        // Decorative waves
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.BottomCenter)
        ) {
            val firstWave = Path().apply {
                moveTo(0f, size.height * 0.55f)
                cubicTo(
                    size.width * 0.25f, size.height * 0.10f,
                    size.width * 0.40f, size.height * 1.05f,
                    size.width, size.height * 0.30f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = firstWave,
                color = if (isDarkTheme) Color(0xFF40345F) else Color(0xFFE5DEFF),
                style = Fill
            )

            val secondWave = Path().apply {
                moveTo(0f, size.height)
                cubicTo(
                    size.width * 0.30f, size.height * 0.25f,
                    size.width * 0.45f, size.height * 1.15f,
                    size.width, size.height * 0.65f
                )
                lineTo(size.width, size.height)
                close()
            }

            drawPath(
                path = secondWave,
                color = if (isDarkTheme) Color(0xFF514272) else Color(0xFFD0C3FF)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = mainText
                )
            }

            Spacer(Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            accent.copy(alpha = 0.15f),
                            RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Column {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = mainText
                    )

                    Text(
                        "Customize your app experience",
                        style = MaterialTheme.typography.bodyMedium,
                        color = secondaryText
                    )
                }
            }

            Spacer(Modifier.height(42.dp))

            // Appearance card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, accent.copy(alpha = 0.18f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.DarkMode,
                            contentDescription = null,
                            tint = accent
                        )

                        Text(
                            "Appearance",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = mainText
                        )
                    }

                    Spacer(Modifier.height(18.dp))
                    HorizontalDivider(color = accent.copy(alpha = 0.12f))
                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Dark theme",
                                fontWeight = FontWeight.SemiBold,
                                color = mainText
                            )

                            Text(
                                "Use a darker color scheme",
                                style = MaterialTheme.typography.bodySmall,
                                color = secondaryText
                            )
                        }

                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = onThemeChange,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = accent,
                                checkedThumbColor = background
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // About card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, accent.copy(alpha = 0.18f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = accent
                        )

                        Text(
                            "About",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = mainText
                        )
                    }

                    Spacer(Modifier.height(18.dp))
                    HorizontalDivider(color = accent.copy(alpha = 0.12f))
                    Spacer(Modifier.height(18.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(
                            Icons.Default.Style,
                            contentDescription = null,
                            tint = accent,
                            modifier = Modifier.size(30.dp)
                        )

                        Column {
                            Text(
                                "FlashQuiz",
                                fontWeight = FontWeight.SemiBold,
                                color = mainText
                            )
                            Text(
                                "v1.0.0",
                                style = MaterialTheme.typography.bodySmall,
                                color = secondaryText
                            )
                        }
                    }

                    Spacer(Modifier.height(18.dp))
                    HorizontalDivider(color = accent.copy(alpha = 0.12f))
                    Spacer(Modifier.height(18.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            tint = accent,
                            modifier = Modifier.size(30.dp)
                        )

                        Column {
                            Text(
                                "Assignment 9",
                                fontWeight = FontWeight.SemiBold,
                                color = mainText
                            )
                            Text(
                                "Room Database & DataStore",
                                style = MaterialTheme.typography.bodySmall,
                                color = secondaryText
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    FlashQuizTheme {
        SettingsScreen(
            isDarkTheme = false,
            onThemeChange = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenDarkPreview() {
    FlashQuizTheme {
        SettingsScreen(
            isDarkTheme = true,
            onThemeChange = {},
            onBack = {}
        )
    }
}
