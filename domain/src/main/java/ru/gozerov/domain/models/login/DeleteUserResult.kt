package ru.gozerov.domain.models.login

sealed class DeleteUserResult {

    object Success: DeleteUserResult()

    object Error: DeleteUserResult()

}
