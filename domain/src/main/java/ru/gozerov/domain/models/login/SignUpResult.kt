package ru.gozerov.domain.models.login

sealed class SignUpResult {

    data class SuccessLogin(
        val username: String
    ) : SignUpResult()

    data object AccountExist : SignUpResult()

    data object UnknownException : SignUpResult()

}
