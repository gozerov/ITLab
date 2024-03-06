package ru.gozerov.domain.models.tags

data class CreateTagData(
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val imagePath: String? = null
)