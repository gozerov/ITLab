package ru.gozerov.domain.models.login

sealed class LoginResult {

    object None: LoginResult()

    object SuccessLogin : LoginResult()

    object BadCredentials : LoginResult()

    object UnknownException : LoginResult()

}
