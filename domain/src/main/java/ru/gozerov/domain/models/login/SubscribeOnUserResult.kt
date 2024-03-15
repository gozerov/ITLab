package ru.gozerov.domain.models.login

sealed class SubscribeOnUserResult {

    class Success(
        val isSubscribed: Boolean
    ): SubscribeOnUserResult()

    object Error: SubscribeOnUserResult()

}