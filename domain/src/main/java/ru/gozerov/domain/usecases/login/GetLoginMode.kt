package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.GetLoginModeResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetLoginMode @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, GetLoginModeResult>() {

    override suspend fun loadData(arg: Unit): Flow<GetLoginModeResult> =
        loginRepository.getLoginMode()

    override suspend fun onError(e: Exception) {
        _result.emit(GetLoginModeResult.Error)
    }

}