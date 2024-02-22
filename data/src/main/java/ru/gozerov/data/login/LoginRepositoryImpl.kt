package ru.gozerov.data.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.models.users.User
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemote: LoginRemote,
    private val userStorage: UserStorage
) : LoginRepository {
    override suspend fun login(username: String, password: String): LoginResult =
        withContext(Dispatchers.IO) {
            val response = loginRemote.login(username, password)
            userStorage.saveUser(token = response.access_token, username = username)
            return@withContext response.toLoginResponse()
        }

    override suspend fun register(username: String, password: String): SignUpResult =
        withContext(Dispatchers.IO) {
            return@withContext loginRemote.register(username, password).toSignUpResponse()
        }

    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        return@withContext userStorage.getUsers()
    }

}