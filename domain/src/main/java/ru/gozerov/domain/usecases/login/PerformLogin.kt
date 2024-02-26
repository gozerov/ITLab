package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class PerformLogin @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginData, LoginResult>() {

    override suspend fun loadData(arg: LoginData): Flow<LoginResult> {
        return loginRepository.login(arg.username, arg.password)
    }

    override suspend fun onError(e: Exception) {
        e.printStackTrace()
        _result.emit(LoginResult.UnknownException)
    }

}