package ru.gozerov.presentation.screens.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.gozerov.presentation.R
import ru.gozerov.presentation.navigation.Screen
import ru.gozerov.presentation.screens.login.models.LoginIntent
import ru.gozerov.presentation.screens.login.models.LoginViewState
import ru.gozerov.presentation.screens.login.views.LoginForm
import ru.gozerov.presentation.screens.shared.showError

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isLoadingState = remember { mutableStateOf(false) }
    val viewState = viewModel.viewState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { contentPadding ->
        LoginForm(
            contentPadding = contentPadding,
            isLoadingState = isLoadingState,
            onLoginClicked = { username, password ->
                viewModel.handleIntent(LoginIntent.PerformLogin(username, password))
            },
            onRegisterClicked = { username, password ->
                viewModel.handleIntent(LoginIntent.PerformRegister(username, password))
            }
        )
        when (viewState) {
            is LoginViewState.None -> {}
            is LoginViewState.Success -> {
                navController.navigate(Screen.MainSection.route)
                viewModel.handleIntent(LoginIntent.Exit)
            }

            is LoginViewState.Loading -> {
                isLoadingState.value = true
            }

            is LoginViewState.BadCredentialsError -> {
                snackbarHostState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.bad_credentials)
                )
            }

            is LoginViewState.AccountExistsError -> {
                snackbarHostState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.account_exists)
                )
            }

            is LoginViewState.UnknownError -> {
                snackbarHostState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.unknown_error)
                )
            }
        }

    }
}