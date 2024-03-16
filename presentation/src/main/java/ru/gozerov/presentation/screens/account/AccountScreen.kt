package ru.gozerov.presentation.screens.account

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.gozerov.domain.models.users.User
import ru.gozerov.presentation.R
import ru.gozerov.presentation.navigation.Screen
import ru.gozerov.presentation.screens.account.models.AccountIntent
import ru.gozerov.presentation.screens.account.models.AccountViewState
import ru.gozerov.presentation.screens.account.views.AccountView
import ru.gozerov.presentation.screens.shared.showError

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val viewState = viewModel.viewState.collectAsState().value
    val currentUser = remember { mutableStateOf<User?>(null) }
    val isGuestMode = remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = null) {
        viewModel.handleIntent(AccountIntent.LoadUser)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        AccountView(
            user = currentUser.value,
            isGuestMode = isGuestMode.value
        ) {
            navController.navigate(Screen.ChooseAccount.route)
        }
    }

    when (viewState) {
        is AccountViewState.None -> {}
        is AccountViewState.Incognito -> {
            currentUser.value = null
            isGuestMode.value = true
        }

        is AccountViewState.LoggedUser -> {
            currentUser.value = viewState.user
            isGuestMode.value = false
        }

        is AccountViewState.Error -> {
            snackbarHostState.showError(
                coroutineScope = coroutineScope,
                message = stringResource(id = R.string.unknown_error)
            )
        }
    }
}