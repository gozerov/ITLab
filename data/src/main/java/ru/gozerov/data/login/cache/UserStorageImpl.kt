package ru.gozerov.data.login.cache

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gozerov.data.login.cache.room.UserDao
import ru.gozerov.data.login.cache.room.UserEntity
import ru.gozerov.data.utils.ApiConstants
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.users.User
import javax.inject.Inject

class UserStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userDao: UserDao
) : UserStorage {

    override fun getCurrentAccessToken(): String? =
        sharedPreferences.getString(ApiConstants.KEY_ACCESS_TOKEN, null)

    override fun saveAccessToken(token: String) {
        sharedPreferences
            .edit()
            .putString(ApiConstants.KEY_ACCESS_TOKEN, token)
            .apply()
    }

    override suspend fun isLoggedUserAuthor(tag: Tag): Boolean {
        val tagUser = tag.user ?: return false
        val token = getCurrentAccessToken() ?: return false
        val user = userDao.getUserByToken(token)
        return user?.username == tagUser.username
    }

    override suspend fun saveUser(token: String, username: String) {
        userDao.addUser(UserEntity(token = token, username = username))
        saveAccessToken(token)
    }

    override suspend fun getUsers(): Flow<List<User>> {
        return userDao.getUsers().map { list -> list.map { userEntity -> userEntity.toUser() } }
    }

    override suspend fun deleteUser(username: String) {
        userDao.deleteUserByName(username)
    }

    override suspend fun refreshActiveAccount(username: String) {
        val user = userDao.getUserByName(username)
        saveAccessToken(user.token)
    }

}