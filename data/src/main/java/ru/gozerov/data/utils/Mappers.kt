package ru.gozerov.data.utils

import ru.gozerov.data.tags.cache.room.TagEntity
import ru.gozerov.data.tags.remote.models.TagResponseBody
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.users.User

fun TagResponseBody.toTag(): Tag =
    Tag(
        id,
        latitude,
        longitude,
        description,
        image?.let { "https://maps.rtuitlab.dev$image" },
        likes,
        is_liked,
        user
    )

fun TagEntity.toTag(): Tag =
    Tag(
        id,
        latitude,
        longitude,
        description,
        image,
        likes,
        isLiked,
        username?.let { User(it) }
    )


fun Tag.toTagEntity(): TagEntity =
    TagEntity(
        id,
        latitude,
        longitude,
        description,
        image,
        likes,
        isLiked,
        user?.username
    )