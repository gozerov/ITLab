package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.LoginResponse
import ru.gozerov.domain.models.SignUpResponse

interface LoginRepository: Repository {

    suspend fun login(username: String, password: String): LoginResponse

    suspend fun register(username: String, password: String): SignUpResponse

}