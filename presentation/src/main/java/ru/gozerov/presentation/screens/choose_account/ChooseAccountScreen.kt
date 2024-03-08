package ru.gozerov.presentation.screens.choose_account

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.gozerov.domain.models.users.User
import ru.gozerov.presentation.R
import ru.gozerov.presentation.navigation.Screen
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountIntent
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountViewState
import ru.gozerov.presentation.screens.choose_account.views.AccountsList
import ru.gozerov.presentation.screens.shared.showError

@Composable
fun ChooseAccountScreen(navController: NavController, viewModel: ChooseAccountViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarScopeState = remember { SnackbarHostState() }
    var isListEmptyState by remember { mutableStateOf(false) }
    val viewState = viewModel.viewState.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        val userList = remember { mutableStateOf<List<User>>(emptyList()) }
        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(ChooseAccountIntent.GetUsers)
        }
        AccountsList(
            contentPadding = contentPadding,
            userList = userList,
            isListEmpty = isListEmptyState,
            onLoginInAnotherAccount = {
                navController.navigate(route = Screen.Login.route)
            },
            onItemClick = {
                viewModel.handleIntent(ChooseAccountIntent.Login(it))
            },
            onCloseClick = {
                viewModel.handleIntent(ChooseAccountIntent.DeleteAccount(it))
            }
        )

        when (viewState) {
            is ChooseAccountViewState.None -> {}
            is ChooseAccountViewState.EmptyList -> {
                isListEmptyState = true
            }

            is ChooseAccountViewState.UserList -> {
                userList.value = viewState.users
            }

            is ChooseAccountViewState.SuccessLogin -> {
                navController.navigate(Screen.MainSection.route)
                viewModel.handleIntent(ChooseAccountIntent.ExitScreen)
            }

            is ChooseAccountViewState.Error -> {
                snackbarScopeState.showError(
                    coroutineScope = coroutineScope,
                    message = stringResource(id = R.string.unknown_error)
                )
            }
        }
    }
}