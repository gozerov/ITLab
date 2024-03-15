package ru.gozerov.domain.usecases.login

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.login.SubscribeOnUserData
import ru.gozerov.domain.models.login.SubscribeOnUserResult
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class SubscribeOnUser @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<SubscribeOnUserData, SubscribeOnUserResult>() {

    override suspend fun loadData(arg: SubscribeOnUserData): Flow<SubscribeOnUserResult> =
        loginRepository.subscribeOnUser(arg.username, arg.isSubscribed)

    override suspend fun onError(e: Exception) {
        _result.emit(SubscribeOnUserResult.Error)
    }

}