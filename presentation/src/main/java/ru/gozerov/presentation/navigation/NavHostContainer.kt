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
import ru.gozerov.presentation.screens.shared.enterAnimation
import ru.gozerov.presentation.screens.shared.exitAnimation

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChooseAccount.route,
        builder = {
            composable(
                route = Screen.ChooseAccount.route,
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                val chooseAccountViewModel = hiltViewModel<ChooseAccountViewModel>()
                ChooseAccountScreen(
                    navController = navController,
                    viewModel = chooseAccountViewModel
                )
            }
            composable(
                route = Screen.Login.route,
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    navController = navController,
                    viewModel = loginViewModel
                )
            }
            composable(
                route = Screen.MainSection.route,
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                MainSection(rootNavController = navController, padding)
            }
        }
    )

}