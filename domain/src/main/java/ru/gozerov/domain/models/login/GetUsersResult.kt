package ru.gozerov.domain.models.login

import ru.gozerov.domain.models.users.User

sealed class GetUsersResult {

    data class Success(
        val users: List<User>
    ): GetUsersResult()

    data object Error: GetUsersResult()

}