package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.models.users.User

interface LoginRepository: Repository {

    suspend fun login(username: String, password: String): LoginResult

    suspend fun register(username: String, password: String): SignUpResult

    suspend fun getUsers(): List<User>

}