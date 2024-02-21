package ru.gozerov.domain.models

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