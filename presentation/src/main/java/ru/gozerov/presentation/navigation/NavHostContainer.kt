package ru.gozerov.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.gozerov.presentation.screens.choose_account.ChooseAccountScreen
import ru.gozerov.presentation.screens.choose_account.ChooseAccountViewModel
import ru.gozerov.presentation.screens.login.LoginScreen
import ru.gozerov.presentation.screens.login.LoginViewModel
import ru.gozerov.presentation.screens.main_section.MainSection

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChooseAccount.route,
        builder = {
            composable(Screen.ChooseAccount.route) {
                val chooseAccountViewModel = hiltViewModel<ChooseAccountViewModel>()
                ChooseAccountScreen(
                    navController = navController,
                    viewModel = chooseAccountViewModel
                )
            }
            composable(Screen.Login.route) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    navController = navController,
                    viewModel = loginViewModel
                )
            }
            composable(Screen.MainSection.route) {
                MainSection(rootNavController = navController, padding)
            }
        }
    )

}