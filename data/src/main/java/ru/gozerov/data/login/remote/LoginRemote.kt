package ru.gozerov.data.login.remote

import ru.gozerov.data.login.remote.models.SuccessLoginResponseBody
import ru.gozerov.data.login.remote.models.SuccessSignUpResponseBody

interface LoginRemote {

    suspend fun login(username: String, password: String): Result<SuccessLoginResponseBody>

    suspend fun register(username: String, password: String): Result<SuccessSignUpResponseBody>

}