package ru.gozerov.domain.models.tags

data class GetTagsByOptionAndUserData(
    val username: String,
    val defaultOption: String,
    val imageOption: String
)
