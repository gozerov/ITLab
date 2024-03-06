package ru.gozerov.domain.models.tags

import android.net.Uri

data class CreateTagData(
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val imageUri: Uri? = null
)