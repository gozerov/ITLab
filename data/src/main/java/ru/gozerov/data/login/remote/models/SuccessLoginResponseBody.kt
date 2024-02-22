package ru.gozerov.data.login.remote.models

import com.squareup.moshi.Json
import ru.gozerov.domain.models.login.LoginResult

data class SuccessLoginResponseBody(
    @Json(name = "access_token")
    val access_token: String
) {
    fun toLoginResponse(): LoginResult.SuccessLogin {
        return LoginResult.SuccessLogin
    }
}