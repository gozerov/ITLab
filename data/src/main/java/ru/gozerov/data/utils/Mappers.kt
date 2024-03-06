package ru.gozerov.data.utils

import ru.gozerov.data.tags.remote.models.TagResponseBody
import ru.gozerov.domain.models.tags.Tag

fun TagResponseBody.toTag(): Tag =
    Tag(id, latitude, longitude, description, image, likes, is_liked, user)