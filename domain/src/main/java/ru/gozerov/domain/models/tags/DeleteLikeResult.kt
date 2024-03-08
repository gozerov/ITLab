package ru.gozerov.domain.models.tags

sealed class DeleteLikeResult {

    class Success(
        val tagDetails: TagDetails
    ) : DeleteLikeResult()

    object Error : DeleteLikeResult()

}
