package ru.gozerov.presentation.screens.login.models

sealed class LoginViewState {

    object Empty: LoginViewState()

    class Loading: LoginViewState()

    class Success: LoginViewState()

    class BadCredentialsError: LoginViewState()

    class AccountExistsError: LoginViewState()

    class UnknownError: LoginViewState()

}
