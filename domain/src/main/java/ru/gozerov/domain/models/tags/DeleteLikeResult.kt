package ru.gozerov.domain.models.tags

sealed class DeleteLikeResult {

    object Success : DeleteLikeResult()

    object Error : DeleteLikeResult()

}
