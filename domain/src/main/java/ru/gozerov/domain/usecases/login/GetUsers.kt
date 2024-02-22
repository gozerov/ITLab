package ru.gozerov.domain.usecases.login

import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, GetUsersResult>() {

    override suspend fun loadData(arg: Unit): GetUsersResult {
        return GetUsersResult.Success(loginRepository.getUsers())
    }

    override suspend fun onError(e: Exception) {
        _result.emit(GetUsersResult.Error)
    }

}