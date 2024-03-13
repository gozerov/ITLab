package ru.gozerov.presentation.screens.account.models

sealed class AccountIntent {

    object LoadUser: AccountIntent()

}