package ru.gozerov.domain.models.tags

sealed class DeleteLikeResult {

    class Success(
        val tag: Tag
    ) : DeleteLikeResult()

    object Error : DeleteLikeResult()

}
