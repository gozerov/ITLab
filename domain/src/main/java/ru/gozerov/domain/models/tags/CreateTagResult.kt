package ru.gozerov.domain.models.tags

sealed class CreateTagResult {

    data class Success(
        val tag: Tag
    ) : CreateTagResult()

    data object Error : CreateTagResult()

}