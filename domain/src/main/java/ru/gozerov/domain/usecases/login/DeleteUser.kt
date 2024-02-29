package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.DeleteUserResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<String, DeleteUserResult>() {

    override suspend fun loadData(arg: String): Flow<DeleteUserResult> =
        loginRepository.deleteUserByName(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(DeleteUserResult.Error)
    }

}