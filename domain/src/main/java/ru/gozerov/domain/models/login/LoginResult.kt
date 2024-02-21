package ru.gozerov.domain.models.login

sealed class LoginResult {

    data object SuccessLogin : LoginResult()

    data object BadCredentials : LoginResult()

    data object UnknownException : LoginResult()

}
