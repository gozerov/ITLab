package ru.gozerov.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.models.users.User

interface LoginRepository: Repository {

    suspend fun login(username: String, password: String): Flow<LoginResult>

    suspend fun register(username: String, password: String): Flow<SignUpResult>

    suspend fun getUsers(): Flow<GetUsersResult>

}