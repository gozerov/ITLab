package ru.gozerov.data.login.remote

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.gozerov.data.login.remote.models.LoginRequestBody
import ru.gozerov.data.login.remote.models.LoginResponseBody
import ru.gozerov.data.login.remote.models.SignUpResponseBody

interface LoginApi {

    @FormUrlEncoded
    @POST("api/auth/jwt/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): LoginResponseBody

    @POST("api/auth/register")
    suspend fun signUp(@Body loginRequestBody: LoginRequestBody): SignUpResponseBody

}