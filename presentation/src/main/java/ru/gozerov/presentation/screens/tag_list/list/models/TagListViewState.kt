package ru.gozerov.presentation.screens.tag_list.list.models

import ru.gozerov.domain.models.tags.Tag

sealed class TagListViewState {

    object None: TagListViewState()

    class TagList(
        val tags: List<Tag>
    ): TagListViewState()

    class Filters(
        val defaultFilters: List<String>,
        val imageFilters: List<String>
    ): TagListViewState()

    class Error: TagListViewState()

}