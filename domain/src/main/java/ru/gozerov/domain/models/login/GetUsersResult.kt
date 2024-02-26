package ru.gozerov.domain.models.login

import ru.gozerov.domain.models.users.User

sealed class GetUsersResult {

    class Success(
        val users: List<User>
    ): GetUsersResult()

    object Error: GetUsersResult()

}