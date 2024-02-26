package ru.gozerov.domain.models.login

sealed class SignUpResult {

    class SuccessLogin(
        val username: String
    ) : SignUpResult()

    object AccountExist : SignUpResult()

    object UnknownException : SignUpResult()

}
