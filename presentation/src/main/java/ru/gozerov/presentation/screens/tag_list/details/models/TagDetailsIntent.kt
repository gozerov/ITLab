package ru.gozerov.presentation.screens.tag_list.details.models

import ru.gozerov.domain.models.tags.Tag

sealed class TagDetailsIntent {

    class LikeTag(val id: String) : TagDetailsIntent()

    class UnlikeTag(val tag: Tag) : TagDetailsIntent()

    class DeleteTag(val id: String) : TagDetailsIntent()

}