package ru.gozerov.domain.models.tags

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gozerov.domain.models.users.User

@Parcelize
data class Tag(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val isLiked: Boolean,
    val user: User?
) : Parcelable