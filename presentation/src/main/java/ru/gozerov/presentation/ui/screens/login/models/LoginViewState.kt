package ru.gozerov.presentation.ui.screens.login.models

sealed class LoginViewState {

    object Empty: LoginViewState()

    class Loading: LoginViewState()

    class Success: LoginViewState()

}
