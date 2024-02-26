package ru.gozerov.data.tags.remote

import ru.gozerov.data.tags.remote.models.CreateTagRequestBody
import ru.gozerov.data.utils.toTag
import ru.gozerov.domain.models.tags.Tag
import javax.inject.Inject

class TagRemoteImpl @Inject constructor(
    private val tagApi: TagApi
) : TagRemote {

    override suspend fun getTags(): Result<List<Tag>> {
        return tagApi.getTags().map { list -> list.map { tagResponse -> tagResponse.toTag() } }
    }

    override suspend fun createTag(
        latitude: Double,
        longitude: Double,
        description: String
    ): Result<Tag> {
        return tagApi.createTag(CreateTagRequestBody(latitude, longitude, description))
            .map { it.toTag() }
    }

    override suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        accessToken: String
    ): Result<Tag> {
        val bearer = "Bearer $accessToken"
        return tagApi.createTagAuthorized(
            CreateTagRequestBody(latitude, longitude, description),
            bearer
        ).map { it.toTag() }
    }

    override suspend fun deleteTagAuthorized(tagId: String, accessToken: String): Result<String> {
        val bearer = "Bearer $accessToken"
        return tagApi.deleteTagAuthorized(tagId, bearer)
    }

    override suspend fun likeTagAuthorized(tagId: String, accessToken: String): Result<Tag> {
        val bearer = "Bearer $accessToken"
        return tagApi.likeTagAuthorized(tagId, bearer).map { it.toTag() }
    }

    override suspend fun deleteLikeAuthorized(tagId: String, accessToken: String): Result<String> {
        val bearer = "Bearer $accessToken"
        return tagApi.deleteLikeFromTagAuthorized(tagId, bearer)
    }

}