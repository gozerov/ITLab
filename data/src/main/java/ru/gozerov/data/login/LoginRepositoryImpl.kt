package ru.gozerov.data.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemote: LoginRemote
) : LoginRepository {
    override suspend fun login(username: String, password: String): LoginResult =
        withContext(Dispatchers.IO) {
            return@withContext loginRemote.login(username, password).toLoginResponse()
        }

    override suspend fun register(username: String, password: String): SignUpResult =
        withContext(Dispatchers.IO) {
            return@withContext loginRemote.register(username, password).toSignUpResponse()
        }

}