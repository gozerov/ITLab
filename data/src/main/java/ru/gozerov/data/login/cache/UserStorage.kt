package ru.gozerov.data.login.cache

import ru.gozerov.domain.models.users.User

interface UserStorage {

    fun getCurrentAccessToken(): String?

    fun saveAccessToken(token: String)

    suspend fun saveUser(token: String, username: String)

    suspend fun getUsers(): List<User>

}