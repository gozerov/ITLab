package ru.gozerov.presentation.screens.main_section

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.gozerov.presentation.navigation.BottomNavHostContainer
import ru.gozerov.presentation.navigation.BottomNavigationBar
import ru.gozerov.presentation.ui.theme.ITLabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSection(rootNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        contentColor = ITLabTheme.colors.controlColor
    ) { contentPadding ->
        BottomNavHostContainer(navController = navController, padding = contentPadding)
    }
}