package ru.gozerov.data.tags.remote.models

data class CreateTagRequestBody(
    val latitude: Double,
    val longitude: Double,
    val description: String
)