package ru.gozerov.domain.models.login

sealed class LoginWithoutPasswordResult {

    object Success: LoginWithoutPasswordResult()

    object Error: LoginWithoutPasswordResult()

}
