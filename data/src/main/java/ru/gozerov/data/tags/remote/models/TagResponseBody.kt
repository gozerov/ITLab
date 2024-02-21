package ru.gozerov.data.tags.remote.models

import com.squareup.moshi.Json
import ru.gozerov.domain.models.Tag
import ru.gozerov.domain.models.User

data class TagResponseBody(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    @Json(name = "is_liked")
    val isLiked: Boolean,
    val user: User?
) {

    fun toTag(): Tag = Tag(id, latitude, longitude, description, image, likes, isLiked, user)

}