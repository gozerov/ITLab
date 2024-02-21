package ru.gozerov.data.tags.remote

import ru.gozerov.domain.models.tags.Tag

interface TagRemote {

    suspend fun getTags(): List<Tag>

    suspend fun createTag(latitude: Double, longitude: Double, description: String): Tag

    suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        accessToken: String
    ): Tag

    suspend fun deleteTagAuthorized(tagId: String, accessToken: String)

    suspend fun likeTagAuthorized(tagId: String, accessToken: String): Tag

    suspend fun deleteLikeAuthorized(tagId: String, accessToken: String)

}