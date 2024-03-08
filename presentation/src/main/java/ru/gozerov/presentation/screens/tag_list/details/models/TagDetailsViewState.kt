package ru.gozerov.presentation.screens.tag_list.details.models

import ru.gozerov.domain.models.tags.Tag

sealed class TagDetailsViewState {

    object None : TagDetailsViewState()

    class UpdatedTag(
        val tag: Tag
    ) : TagDetailsViewState()

    class Error : TagDetailsViewState()

    class TagHasBeenDeleted: TagDetailsViewState()

}