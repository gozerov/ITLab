package ru.gozerov.domain.models.tags

sealed class LikeTagResult {

    class Success(
        val tagDetails: TagDetails
    ) : LikeTagResult()

    object Error: LikeTagResult()

}
