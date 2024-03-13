package ru.gozerov.presentation.screens.account.models

import ru.gozerov.domain.models.users.User

sealed class AccountViewState {

    object None: AccountViewState()

    class LoggedUser(
        val user: User
    ): AccountViewState()

    class Incognito: AccountViewState()

    class Error: AccountViewState()

}