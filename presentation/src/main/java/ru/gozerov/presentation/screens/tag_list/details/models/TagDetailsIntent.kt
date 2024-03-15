package ru.gozerov.presentation.screens.tag_list.details.models

import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.presentation.screens.tag_map.models.TagMapIntent

sealed class TagDetailsIntent {

    class LoadTag(val id: String) : TagDetailsIntent()

    class LikeTag(val id: String) : TagDetailsIntent()

    class UnlikeTag(val tag: Tag) : TagDetailsIntent()

    class DeleteTag(val id: String) : TagDetailsIntent()

    object GetLoginMode : TagDetailsIntent()

    class SubscribeOnAuthor(val username: String, val subscribed: Boolean) : TagDetailsIntent()

}