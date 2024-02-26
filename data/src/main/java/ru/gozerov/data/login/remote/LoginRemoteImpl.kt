package ru.gozerov.data.login.remote

import ru.gozerov.data.login.remote.models.LoginRequestBody
import ru.gozerov.data.login.remote.models.SuccessLoginResponseBody
import ru.gozerov.data.login.remote.models.SuccessSignUpResponseBody
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemote {

    override suspend fun login(username: String, password: String): Result<SuccessLoginResponseBody> {
        return loginApi.login(username, password)
    }

    override suspend fun register(username: String, password: String): Result<SuccessSignUpResponseBody> {
        return loginApi.signUp(LoginRequestBody(username, password))
    }


}