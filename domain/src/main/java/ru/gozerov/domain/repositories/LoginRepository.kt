package ru.gozerov.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.DeleteUserResult
import ru.gozerov.domain.models.login.GetCurrentUserResult
import ru.gozerov.domain.models.login.GetLoginModeResult
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.GuestModeResult
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.LoginWithoutPasswordResult
import ru.gozerov.domain.models.login.SignUpResult

interface LoginRepository : Repository {

    suspend fun checkFirstRun(): Boolean

    suspend fun login(username: String, password: String): Flow<LoginResult>

    suspend fun guestMode(): Flow<GuestModeResult>

    suspend fun getLoginMode(): Flow<GetLoginModeResult>

    suspend fun register(username: String, password: String): Flow<SignUpResult>

    suspend fun getUsers(): Flow<GetUsersResult>

    suspend fun getCurrentUser(): Flow<GetCurrentUserResult>

    suspend fun deleteUserByName(name: String): Flow<DeleteUserResult>

    suspend fun loginWithoutPassword(username: String): Flow<LoginWithoutPasswordResult>

}