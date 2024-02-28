package ru.gozerov.presentation.screens.login.views

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showError(
    coroutineScope: CoroutineScope,
    isLoadingState: MutableState<Boolean>,
    message: String
) {
    isLoadingState.value = false
    coroutineScope.launch {
        showSnackbar(
            message = message,
        )
    }
}