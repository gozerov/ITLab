package ru.gozerov.presentation.screens.choose_account.models

import ru.gozerov.domain.models.users.User

sealed class ChooseAccountViewState {

    class None : ChooseAccountViewState()

    class EmptyList : ChooseAccountViewState()

    class UserList(
        val users: List<User>
    ) : ChooseAccountViewState()

    class Error : ChooseAccountViewState()

}