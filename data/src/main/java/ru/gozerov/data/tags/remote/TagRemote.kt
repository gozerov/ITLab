package ru.gozerov.data.tags.remote

import android.net.Uri
import ru.gozerov.domain.models.tags.Tag

interface TagRemote {

    suspend fun getTags(): Result<List<Tag>>

    suspend fun getTagsByOption(defaultOption: String, imageOption: String): Result<List<Tag>>

    suspend fun getTagsByOptionAndUser(
        username: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>>

    suspend fun getTagsAuthorized(accessToken: String): Result<List<Tag>>

    suspend fun getTagsByOptionAuthorized(
        accessToken: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>>

    suspend fun getTagsByOptionAndUserAuthorized(
        accessToken: String,
        username: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>>

    suspend fun createTag(
        latitude: Double,
        longitude: Double,
        description: String,
        imageUri: Uri? = null
    ): Result<Tag>

    suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        imageUri: Uri? = null,
        accessToken: String
    ): Result<Tag>

    suspend fun deleteTagAuthorized(tagId: String, accessToken: String): Result<String>

    suspend fun likeTagAuthorized(tagId: String, accessToken: String): Result<Tag>

    suspend fun deleteLikeAuthorized(tagId: String, accessToken: String): Result<String>

}