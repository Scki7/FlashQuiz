package com.example.flashquiz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Accent,
    onSecondary = Color.White,
    background = BackgroundLight,
    onBackground = TextDark,
    surface = BackgroundLight,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFE5E7EB),
    onSurfaceVariant = Color(0xFF4B5563),
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF5EEAD4),
    onPrimary = Color(0xFF111827),

    secondary = Color(0xFFFDBA74),
    onSecondary = Color(0xFF111827),

    background = BackgroundDark,
    onBackground = TextLight,

    surface = SurfaceDark,
    onSurface = TextLight,

    surfaceVariant = Color(0xFF454151),
    onSurfaceVariant = Color(0xFFD8D3E2),

    error = Color(0xFFFCA5A5)
)
@Composable
fun FlashQuizTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}