package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult

interface LoginRepository: Repository {

    suspend fun login(username: String, password: String): LoginResult

    suspend fun register(username: String, password: String): SignUpResult

}