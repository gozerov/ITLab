package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class PerformRegister @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginData, SignUpResult>() {

    override suspend fun loadData(arg: LoginData): Flow<SignUpResult> {
        return loginRepository.register(arg.username, arg.password)
    }

    override suspend fun onError(e: Exception) {
        _result.emit(SignUpResult.UnknownException)
    }

}