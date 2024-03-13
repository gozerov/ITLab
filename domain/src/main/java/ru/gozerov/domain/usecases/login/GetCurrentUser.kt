package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.GetCurrentUserResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, GetCurrentUserResult>() {

    override suspend fun loadData(arg: Unit): Flow<GetCurrentUserResult> =
        loginRepository.getCurrentUser()

    override suspend fun onError(e: Exception) {
        _result.emit(GetCurrentUserResult.Error)
    }

}