package ru.gozerov.presentation.screens.tag_map.models

import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.Tag

sealed class TagMapIntent {

    object LoadTags : TagMapIntent()

    class LikeTag(val tag: Tag) : TagMapIntent()

    class UnlikeTag(val tag: Tag) : TagMapIntent()

    class CreateTag(
        val createTagData: CreateTagData
    ) : TagMapIntent()

}