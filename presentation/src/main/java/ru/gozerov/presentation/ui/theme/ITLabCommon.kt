package ru.gozerov.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ITLabColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color
)

data class ITLabTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class ITLabShape(
    val padding: Dp,
    val cornersStyle: Shape
)

object ITLabTheme {
    val colors: ITLabColors
        @Composable
        get() = LocalITLabColors.current

    val typography: ITLabTypography
        @Composable
        get() = LocalITLabTypography.current

    val shapes: ITLabShape
        @Composable
        get() = LocalITLabShape.current
}

enum class ITLabSize {
    Small, Medium, Big
}

enum class ITLabCorners {
    Flat, Rounded
}

val LocalITLabColors = staticCompositionLocalOf<ITLabColors> {
    error("No colors provided")
}

val LocalITLabTypography = staticCompositionLocalOf<ITLabTypography> {
    error("No font provided")
}

val LocalITLabShape = staticCompositionLocalOf<ITLabShape> {
    error("No shapes provided")
}