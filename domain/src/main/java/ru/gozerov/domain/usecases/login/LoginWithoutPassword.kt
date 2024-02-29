package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.LoginWithoutPasswordResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class LoginWithoutPassword @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<String, LoginWithoutPasswordResult>() {

    override suspend fun loadData(arg: String): Flow<LoginWithoutPasswordResult> =
        loginRepository.loginWithoutPassword(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(LoginWithoutPasswordResult.Error)
    }

}