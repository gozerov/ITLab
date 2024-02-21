package ru.gozerov.domain.models.tags

sealed class LikeTagResult {

    data class Success(
        val tag: Tag
    ) : LikeTagResult()

    data object Error: LikeTagResult()

}
