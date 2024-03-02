package ru.gozerov.presentation.screens.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun SetupSystemBars(
    statusBarColor: Color? = null,
    useLightIcons: Boolean? = null
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons =
        if (isSystemInDarkTheme() && useLightIcons != null) useLightIcons else !isSystemInDarkTheme()
    val navigationBarColor = ITLabTheme.colors.primaryBackground
    val mergedStatusBarColor = statusBarColor ?: ITLabTheme.colors.statusBarColor
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = mergedStatusBarColor,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = useDarkIcons
        )
        onDispose { }
    }
}