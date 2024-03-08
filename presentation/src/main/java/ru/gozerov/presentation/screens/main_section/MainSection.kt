package ru.gozerov.presentation.screens.main_section

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.gozerov.presentation.navigation.BottomNavHostContainer
import ru.gozerov.presentation.navigation.BottomNavigationBar
import ru.gozerov.presentation.ui.theme.ITLabTheme

@Composable
fun MainSection(rootNavController: NavHostController, contentPaddingValues: PaddingValues) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, contentPaddingValues)
        },
        contentColor = ITLabTheme.colors.controlColor
    ) { contentPadding ->
        BottomNavHostContainer(navController = navController, padding = contentPadding)
    }
}