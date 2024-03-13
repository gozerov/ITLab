package ru.gozerov.domain.models.login

sealed class GetLoginModeResult {

    class Success(
        val mode: LoginMode
    ) : GetLoginModeResult()

    object Error : GetLoginModeResult()

}