package ru.gozerov.domain.models.login

sealed class GuestModeResult {

    object Success: GuestModeResult()

    object Error: GuestModeResult()

}