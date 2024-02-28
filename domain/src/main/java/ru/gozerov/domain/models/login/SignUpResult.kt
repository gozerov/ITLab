package ru.gozerov.domain.models.login

sealed class SignUpResult {

    object Loading : SignUpResult()

    class SuccessLogin(
        val username: String
    ) : SignUpResult()

    object AccountExist : SignUpResult()

    object UnknownException : SignUpResult()

}
