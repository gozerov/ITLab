package ru.gozerov.domain.models.tags

import ru.gozerov.domain.models.users.User

data class Tag(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val isLiked: Boolean,
    val user: User?
)