package ru.gozerov.domain.models.login

import ru.gozerov.domain.models.users.User

sealed class GetCurrentUserResult {

    class Success(
        val user: User
    ): GetCurrentUserResult()

    object Incognito: GetCurrentUserResult()

    object Error: GetCurrentUserResult()

}