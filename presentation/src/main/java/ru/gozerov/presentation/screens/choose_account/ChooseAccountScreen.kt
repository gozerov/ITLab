package ru.gozerov.presentation.screens.choose_account

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.gozerov.domain.models.users.User
import ru.gozerov.presentation.navigation.Screen
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountIntent
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountViewState
import ru.gozerov.presentation.screens.choose_account.views.AccountsList

@Composable
fun ChooseAccountScreen(navController: NavController, viewModel: ChooseAccountViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarScopeState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarScopeState)
        }
    ) { contentPadding ->
        val userList = remember { mutableStateOf<List<User>>(emptyList()) }
        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(ChooseAccountIntent.GetUsers())
        }
        val viewState = viewModel.viewState.collectAsState()
        AccountsList(
            contentPadding = contentPadding,
            userList = userList,
            onLoginInAnotherAccount = {
                navController.navigate(route = Screen.Login.route)
            }
        )

        when (viewState.value) {
            is ChooseAccountViewState.None -> {}
            is ChooseAccountViewState.EmptyList -> {
                userList.value = emptyList()
            }

            is ChooseAccountViewState.UserList -> {
                userList.value = (viewState.value as ChooseAccountViewState.UserList).users
            }

            is ChooseAccountViewState.Error -> {

            }
        }
    }
}