package ru.gozerov.domain.models.tags

sealed class DeleteLikeResult {

    data object Success : DeleteLikeResult()

    data object Error : DeleteLikeResult()

}
