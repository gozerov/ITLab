package ru.gozerov.data.login.remote.models

import com.squareup.moshi.Json
import ru.gozerov.domain.models.LoginResponse

data class SuccessLoginResponseBody(
    @Json(name = "access_token")
    val accessToken: String
) {
    fun toLoginResponse(): LoginResponse.SuccessLogin {
        return LoginResponse.SuccessLogin
    }
}