package ru.gozerov.domain.models.tags

sealed class CreateTagResult {

    class Success(
        val tag: Tag
    ) : CreateTagResult()

    object Error : CreateTagResult()

}