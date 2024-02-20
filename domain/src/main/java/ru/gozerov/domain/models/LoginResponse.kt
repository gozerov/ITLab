package ru.gozerov.domain.models

sealed class LoginResponse {

    data object SuccessLogin: LoginResponse()

    data object BadCredentials : LoginResponse()

}
