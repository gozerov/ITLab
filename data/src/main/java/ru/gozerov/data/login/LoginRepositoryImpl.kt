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
import ru.gozerov.domain.models.login.GetCurrentUserResult
import ru.gozerov.domain.models.login.GetLoginModeResult
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.GuestModeResult
import ru.gozerov.domain.models.login.LoginMode
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.LoginWithoutPasswordResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.models.login.SubscribeOnUserResult
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemote: LoginRemote,
    private val userStorage: UserStorage
) : LoginRepository {

    override suspend fun checkFirstRun(): Boolean = withContext(Dispatchers.IO) {
        return@withContext userStorage.checkFirstRun()
    }

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

    override suspend fun guestMode(): Flow<GuestModeResult> = withContext(Dispatchers.IO) {
        return@withContext flow {
            userStorage.clearAccessToken()
            emit(GuestModeResult.Success)
        }
    }

    override suspend fun getLoginMode(): Flow<GetLoginModeResult> = withContext(Dispatchers.IO) {
        return@withContext flow {
            userStorage.getCurrentAccessToken()?.let {
                emit(GetLoginModeResult.Success(LoginMode.LOGGED))
            } ?: emit(GetLoginModeResult.Success(LoginMode.GUEST))
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

    override suspend fun getCurrentUser(): Flow<GetCurrentUserResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<GetCurrentUserResult> {
                val token = userStorage.getCurrentAccessToken()
                token?.let {
                    val user = userStorage.getUserByToken(token)
                    user?.let {
                        emit(GetCurrentUserResult.Success(user))
                    }
                } ?: emit(GetCurrentUserResult.Incognito)
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

    override suspend fun subscribeOnUser(
        username: String,
        isSubscribed: Boolean
    ): Flow<SubscribeOnUserResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(
                    SubscribeOnUserResult.Success(
                        userStorage.subscribeOnUser(
                            username,
                            isSubscribed
                        )
                    )
                )
            }
        }

}