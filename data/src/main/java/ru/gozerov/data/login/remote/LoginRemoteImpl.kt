package ru.gozerov.data.login.remote

import ru.gozerov.data.login.remote.models.LoginRequestBody
import ru.gozerov.data.login.remote.models.LoginResponseBody
import ru.gozerov.data.login.remote.models.SignUpResponseBody
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemote {

    override suspend fun login(username: String, password: String): LoginResponseBody {
        return loginApi.login(username, password)
    }

    override suspend fun register(username: String, password: String): SignUpResponseBody {
        return loginApi.signUp(LoginRequestBody(username, password))
    }


}