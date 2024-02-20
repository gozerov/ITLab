package ru.gozerov.data.login.remote

import ru.gozerov.data.login.remote.models.LoginResponseBody
import ru.gozerov.data.login.remote.models.SignUpResponseBody

interface LoginRemote {

    suspend fun login(username: String, password: String): LoginResponseBody

    suspend fun register(username: String, password: String): SignUpResponseBody

}