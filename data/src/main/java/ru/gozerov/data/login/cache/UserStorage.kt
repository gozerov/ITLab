package ru.gozerov.data.login.cache

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.users.User

interface UserStorage {

    fun getCurrentAccessToken(): String?

    fun saveAccessToken(token: String)

    suspend fun isLoggedUserAuthor(tag: Tag): Boolean

    suspend fun saveUser(token: String, username: String)

    suspend fun getUsers(): Flow<List<User>>

    suspend fun deleteUser(username: String)

    suspend fun refreshActiveAccount(username: String)

}