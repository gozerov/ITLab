package ru.gozerov.data.login.remote.models

import com.squareup.moshi.Json

data class SuccessLoginResponseBody(
    @Json(name = "access_token")
    val access_token: String
)