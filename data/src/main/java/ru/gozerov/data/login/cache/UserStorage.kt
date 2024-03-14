package ru.gozerov.data.login.cache

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.users.User

interface UserStorage {

    fun checkFirstRun(): Boolean

    fun getCurrentAccessToken(): String?

    fun saveAccessToken(token: String)

    fun clearAccessToken()

    suspend fun subscribeOnUser(username: String, isSubscribed: Boolean): Boolean

    suspend fun checkSubscription(username: String): Boolean

    suspend fun isLoggedUserAuthor(tag: Tag): Boolean

    suspend fun saveUser(token: String, username: String)

    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUserByToken(token: String): User?

    suspend fun deleteUser(username: String)

    suspend fun refreshActiveAccount(username: String)

}