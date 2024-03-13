package ru.gozerov.domain.usecases.login

import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class CheckFirstRun @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun execute(): Boolean = loginRepository.checkFirstRun()

}