package ru.gozerov.presentation.screens.tag_list.list.models

import ru.gozerov.domain.models.tags.Tag

sealed class TagListViewState {

    object None: TagListViewState()

    class TagList(
        val tags: List<Tag>
    ): TagListViewState()

    class Error: TagListViewState()

}