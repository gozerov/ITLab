package ru.gozerov.presentation.screens.tag_list.details.models

import ru.gozerov.domain.models.tags.TagDetails

sealed class TagDetailsViewState {

    object None : TagDetailsViewState()

    class UpdatedTag(
        val tagDetails: TagDetails
    ) : TagDetailsViewState()

    class Error : TagDetailsViewState()

    class TagHasBeenDeleted : TagDetailsViewState()

}