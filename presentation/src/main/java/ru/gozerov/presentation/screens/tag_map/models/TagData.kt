package ru.gozerov.presentation.screens.tag_map.models

import androidx.compose.runtime.MutableState
import ru.gozerov.domain.models.tags.Tag

internal data class TagData(
    val pickedTagState: MutableState<Tag?>,
    val newTag: Tag
)