package ru.gozerov.data.login.cache

import android.content.SharedPreferences
import ru.gozerov.data.utils.ApiConstants
import javax.inject.Inject

class LoginStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LoginStorage {

    override fun getAccessToken(): String? =
        sharedPreferences.getString(ApiConstants.KEY_ACCESS_TOKEN, null)

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(ApiConstants.KEY_ACCESS_TOKEN, token)
            .apply()
    }

}