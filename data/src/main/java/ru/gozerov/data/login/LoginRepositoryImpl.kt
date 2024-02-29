package ru.gozerov.data.login

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.data.utils.ApiConstants
import ru.gozerov.domain.models.login.DeleteUserResult
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.LoginWithoutPasswordResult
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
                            if (message == ApiConstants.HTTP_400)
                                emit(SignUpResult.AccountExist)
                            else
                                emit(SignUpResult.UnknownException)
                        }
                    }
            }
        }

    override suspend fun getUsers(): Flow<GetUsersResult> = withContext(Dispatchers.IO) {
        return@withContext flow<GetUsersResult> {
            userStorage.getUsers()
                .onEach { users ->
                    if (users.isEmpty())
                        emit(GetUsersResult.EmptyList)
                    else
                        emit(GetUsersResult.Success(users))
                }
                .collect()
        }
    }

    override suspend fun deleteUserByName(name: String): Flow<DeleteUserResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<DeleteUserResult> {
                userStorage.deleteUser(name)
                emit(DeleteUserResult.Success)
            }
        }

    override suspend fun loginWithoutPassword(username: String): Flow<LoginWithoutPasswordResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<LoginWithoutPasswordResult> {
                userStorage.refreshActiveAccount(username)
                emit(LoginWithoutPasswordResult.Success)
            }
        }

}