package ru.gozerov.data.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemote: LoginRemote,
    private val userStorage: UserStorage
) : LoginRepository {
    override suspend fun login(username: String, password: String): Flow<LoginResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<LoginResult> {
                emit(LoginResult.Loading)
                val response = loginRemote.login(username, password)
                response
                    .onSuccess {
                        userStorage.saveUser(it.access_token, username)
                        emit(LoginResult.SuccessLogin)
                    }
                    .onFailure { throwable ->
                        throwable.message?.let { message ->
                            if (message == "HTTP 400 ")
                                emit(LoginResult.BadCredentials)
                            else
                                emit(LoginResult.UnknownException)
                        }
                    }
            }
        }


    override suspend fun register(username: String, password: String): Flow<SignUpResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<SignUpResult> {
                emit(SignUpResult.Loading)
                val response = loginRemote.register(username, password)
                response
                    .onSuccess {
                        val loginResponse = loginRemote.login(username, password)
                        loginResponse.onSuccess { loginBody ->
                            userStorage.saveUser(loginBody.access_token, username)
                            emit(SignUpResult.SuccessLogin(response.getOrThrow().username))
                        }
                    }
                    .onFailure { throwable ->
                        throwable.message?.let { message ->
                            if (message == "HTTP 400 ")
                                emit(SignUpResult.AccountExist)
                            else
                                emit(SignUpResult.UnknownException)
                        }
                    }
            }
        }

    override suspend fun getUsers(): Flow<GetUsersResult> = withContext(Dispatchers.IO) {
        return@withContext flow<GetUsersResult> {
            emit(GetUsersResult.Success(userStorage.getUsers()))
        }
    }

}