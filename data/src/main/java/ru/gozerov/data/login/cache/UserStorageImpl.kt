package ru.gozerov.data.login.cache

import android.content.SharedPreferences
import ru.gozerov.data.login.cache.room.UserDao
import ru.gozerov.data.login.cache.room.UserEntity
import ru.gozerov.data.utils.ApiConstants
import ru.gozerov.domain.models.users.User
import javax.inject.Inject

class UserStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userDao: UserDao
) : UserStorage {

    override fun getCurrentAccessToken(): String? =
        sharedPreferences.getString(ApiConstants.KEY_ACCESS_TOKEN, null)

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(ApiConstants.KEY_ACCESS_TOKEN, token)
            .apply()
    }

    override suspend fun saveUser(token: String, username: String) {
        userDao.addUser(UserEntity(token = token, username = username))
        saveAccessToken(token)
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getUsers().map { it.toUser() }
    }

}