package ru.gozerov.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun ITLabTheme(
    textSize: ITLabSize = ITLabSize.Medium,
    paddingSize: ITLabSize = ITLabSize.Medium,
    corners: ITLabCorners = ITLabCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) baseDarkPalette else baseLightPalette

    val typography = ITLabTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                ITLabSize.Small -> 24.sp
                ITLabSize.Medium -> 28.sp
                ITLabSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                ITLabSize.Small -> 14.sp
                ITLabSize.Medium -> 16.sp
                ITLabSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                ITLabSize.Small -> 14.sp
                ITLabSize.Medium -> 16.sp
                ITLabSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                ITLabSize.Small -> 10.sp
                ITLabSize.Medium -> 12.sp
                ITLabSize.Big -> 14.sp
            }
        )
    )

    val shapes = ITLabShape(
        padding = when (paddingSize) {
            ITLabSize.Small -> 12.dp
            ITLabSize.Medium -> 16.dp
            ITLabSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            ITLabCorners.Flat -> RoundedCornerShape(0.dp)
            ITLabCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if (darkTheme) {
                window.statusBarColor = baseDarkPalette.primaryBackground.toArgb()
                window.navigationBarColor = baseDarkPalette.navigationBarColor.toArgb()
            }
            else {
                window.statusBarColor = baseLightPalette.primaryBackground.toArgb()
                window.navigationBarColor = baseLightPalette.navigationBarColor.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    CompositionLocalProvider(
        LocalITLabColors provides colors,
        LocalITLabTypography provides typography,
        LocalITLabShape provides shapes,
        content = content
    )
}