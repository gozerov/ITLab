package ru.gozerov.presentation.screens.tag_map.models

import ru.gozerov.domain.models.tags.Tag

sealed class TagMapViewState {

    object None: TagMapViewState()

    class TagsOnMap(
        val tags: List<Tag>
    ) : TagMapViewState()

    class Error: TagMapViewState()

}
