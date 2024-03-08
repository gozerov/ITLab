package ru.gozerov.domain.models.users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String
): Parcelable
