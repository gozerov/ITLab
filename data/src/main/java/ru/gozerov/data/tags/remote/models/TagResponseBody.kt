package ru.gozerov.data.tags.remote.models

import ru.gozerov.domain.models.users.User

data class TagResponseBody(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val is_liked: Boolean,
    val user: User?
)