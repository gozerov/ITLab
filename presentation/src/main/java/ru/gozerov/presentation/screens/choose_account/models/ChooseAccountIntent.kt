package ru.gozerov.presentation.screens.choose_account.models

import ru.gozerov.domain.models.users.User

sealed class ChooseAccountIntent {

    object GetUsers : ChooseAccountIntent()

    class Login(val user: User) : ChooseAccountIntent()

    class DeleteAccount(val user: User) : ChooseAccountIntent()

    object ExitScreen : ChooseAccountIntent()

}
