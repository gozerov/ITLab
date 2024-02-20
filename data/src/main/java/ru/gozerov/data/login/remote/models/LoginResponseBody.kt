package ru.gozerov.data.login.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.gozerov.domain.models.LoginResponse

sealed class LoginResponseBody {

    data class SuccessLogin(
        val accessToken: String
    ) : LoginResponseBody()

    data class BadCredentials(
        val detail: String
    ) : LoginResponseBody()

    fun toLoginResponse(): LoginResponse {
        return when (this) {
            is SuccessLogin -> LoginResponse.SuccessLogin
            is BadCredentials -> LoginResponse.BadCredentials
        }
    }

}