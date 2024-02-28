package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.gozerov.presentation.screens.login.LoginScreen
import ru.gozerov.presentation.screens.login.LoginViewModel

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Screen.Login.route) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(navController = navController, viewModel = loginViewModel)
            }
        })

}