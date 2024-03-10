package ru.gozerov.presentation.screens.tag_list.list.models

import ru.gozerov.domain.models.tags.FilterOption

sealed class TagListIntent {

    object LoadTags : TagListIntent()

    object LoadFilters: TagListIntent()

    class GetTagsByUser(val username: String) : TagListIntent()

    class LoadTagsByFilters(
        val defaultOption: String,
        val imageOption: String
    ): TagListIntent()

}