package ru.gozerov.presentation.screens.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
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
    val snackbarScopeState = remember { SnackbarHostState() }
    val isLoadingState = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        val viewState = viewModel.viewState.collectAsState()
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
        when (viewState.value) {
            is LoginViewState.None -> {}
            is LoginViewState.Success -> {
                coroutineScope.launch {
                    snackbarScopeState.showSnackbar(
                        message = "Success",
                    )
                }
            }

            is LoginViewState.Loading -> {
                isLoadingState.value = true
            }

            is LoginViewState.BadCredentialsError -> {
                snackbarScopeState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.bad_credentials)
                )
            }

            is LoginViewState.AccountExistsError -> {
                snackbarScopeState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.account_exists)
                )
            }

            is LoginViewState.UnknownError -> {
                snackbarScopeState.showError(
                    coroutineScope = coroutineScope,
                    isLoadingState = isLoadingState,
                    message = stringResource(id = R.string.unknown_error)
                )
            }
        }

    }
}