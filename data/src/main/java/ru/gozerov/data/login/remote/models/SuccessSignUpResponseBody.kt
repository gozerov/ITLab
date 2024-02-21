package ru.gozerov.data.login.remote.models

import ru.gozerov.domain.models.SignUpResponse


data class SuccessSignUpResponseBody(
    val id: String,
    val username: String
) {
    fun toSignUpResponse(): SignUpResponse.SuccessLogin {
        return SignUpResponse.SuccessLogin(username)
    }

}
