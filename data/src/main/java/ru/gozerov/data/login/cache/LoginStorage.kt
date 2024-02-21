package ru.gozerov.data.login.cache

interface LoginStorage {

    fun getAccessToken(): String?

    fun saveAccessToken(token: String)

}