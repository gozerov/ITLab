package ru.gozerov.domain.models

sealed class SignUpResponse {

    data class SuccessLogin(
        val username: String
    ) : SignUpResponse()

    data object AccountExist : SignUpResponse()

}
