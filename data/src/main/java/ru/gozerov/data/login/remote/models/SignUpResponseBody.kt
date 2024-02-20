package ru.gozerov.data.login.remote.models

import com.squareup.moshi.JsonClass
import ru.gozerov.domain.models.SignUpResponse

sealed class SignUpResponseBody {

    data class SuccessSignUp(
        val id: String,
        val username: String
    ) : SignUpResponseBody()

    data class AccountExist(
        val detail: String
    ) : SignUpResponseBody()

    fun toSignUpResponse(): SignUpResponse {
        return when (this) {
            is SuccessSignUp -> SignUpResponse.SuccessLogin(username)
            is AccountExist -> SignUpResponse.AccountExist
        }
    }

}
