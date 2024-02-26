package ru.gozerov.domain.models.tags

sealed class LikeTagResult {

    class Success(
        val tag: Tag
    ) : LikeTagResult()

    object Error: LikeTagResult()

}
