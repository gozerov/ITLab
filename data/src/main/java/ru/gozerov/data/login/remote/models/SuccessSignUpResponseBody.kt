package ru.gozerov.data.login.remote.models

import ru.gozerov.domain.models.login.SignUpResult


data class SuccessSignUpResponseBody(
    val id: String,
    val username: String
) {
    fun toSignUpResponse(): SignUpResult.SuccessLogin {
        return SignUpResult.SuccessLogin(username)
    }

}
