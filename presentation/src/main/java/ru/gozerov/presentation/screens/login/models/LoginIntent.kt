package ru.gozerov.presentation.screens.login.models

sealed class LoginIntent {

    class PerformLogin(
        val username: String,
        val password: String
    ): LoginIntent()

    class PerformRegister(
        val username: String,
        val password: String
    ): LoginIntent()

    object Exit : LoginIntent()

}
