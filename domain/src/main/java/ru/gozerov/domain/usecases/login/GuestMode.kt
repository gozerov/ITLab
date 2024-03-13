package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.GuestModeResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GuestMode @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<Unit, GuestModeResult>() {

    override suspend fun loadData(arg: Unit): Flow<GuestModeResult> =
        loginRepository.guestMode()

    override suspend fun onError(e: Exception) {
        _result.emit(GuestModeResult.Error)
    }

}