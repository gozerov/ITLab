package ru.gozerov.data.login.cache

import android.content.SharedPreferences
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gozerov.data.database.DBConstants
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

    override fun checkFirstRun(): Boolean {
        return if (sharedPreferences.getBoolean(DBConstants.IS_FIRST_RUN, true)) {
            sharedPreferences
                .edit()
                .putBoolean(DBConstants.IS_FIRST_RUN, false)
                .apply()
            true
        } else
            false
    }

    override fun getCurrentAccessToken(): String? =
        sharedPreferences.getString(ApiConstants.KEY_ACCESS_TOKEN, null)

    override fun saveAccessToken(token: String) {
        sharedPreferences
            .edit()
            .putString(ApiConstants.KEY_ACCESS_TOKEN, token)
            .apply()
    }

    override fun clearAccessToken() {
        sharedPreferences
            .edit()
            .remove(ApiConstants.KEY_ACCESS_TOKEN)
            .apply()
    }

    override suspend fun subscribeOnUser(username: String, isSubscribed: Boolean): Boolean {
        val users = sharedPreferences.getStringSet(DBConstants.KEY_SUBSCRIBED_USERS, emptySet())
        val newUsers = users?.toMutableSet()
        if (isSubscribed) {
            newUsers?.remove(username)
            FirebaseMessaging.getInstance().unsubscribeFromTopic(username)
            sharedPreferences
                .edit()
                .putStringSet(DBConstants.KEY_SUBSCRIBED_USERS, newUsers)
                .apply()
            return false
        } else {
            newUsers?.add(username)
            FirebaseMessaging.getInstance().subscribeToTopic(username)
            sharedPreferences
                .edit()
                .putStringSet(DBConstants.KEY_SUBSCRIBED_USERS, newUsers)
                .apply()
            return true
        }
    }

    override suspend fun checkSubscription(username: String): Boolean {
        val users = sharedPreferences.getStringSet(DBConstants.KEY_SUBSCRIBED_USERS, emptySet())
        return if (!users.isNullOrEmpty()) {
            val user = users.firstOrNull { it == username }
            user?.let {
                true
            } ?: false
        } else
            false
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

    override suspend fun getUserByToken(token: String): User? {
        return userDao.getUserByToken(token)?.toUser()
    }

    override suspend fun deleteUser(username: String) {
        userDao.deleteUserByName(username)
    }

    override suspend fun refreshActiveAccount(username: String) {
        val user = userDao.getUserByName(username)
        saveAccessToken(user.token)
    }

}