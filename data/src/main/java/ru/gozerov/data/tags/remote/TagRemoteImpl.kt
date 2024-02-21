package ru.gozerov.data.tags.remote

import ru.gozerov.data.tags.remote.models.CreateTagRequestBody
import ru.gozerov.domain.models.tags.Tag
import javax.inject.Inject

class TagRemoteImpl @Inject constructor(
    private val tagApi: TagApi
) : TagRemote {

    override suspend fun getTags(): List<Tag> {
        return tagApi.getTags().map { it.toTag() }
    }

    override suspend fun createTag(latitude: Double, longitude: Double, description: String): Tag {
        return tagApi.createTag(CreateTagRequestBody(latitude, longitude, description)).toTag()
    }

    override suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        accessToken: String
    ): Tag {
        val bearer = "Bearer $accessToken"
        return tagApi.createTagAuthorized(
            CreateTagRequestBody(latitude, longitude, description),
            bearer
        ).toTag()
    }

    override suspend fun deleteTagAuthorized(tagId: String, accessToken: String) {
        val bearer = "Bearer $accessToken"
        tagApi.deleteTagAuthorized(tagId, bearer)
    }

    override suspend fun likeTagAuthorized(tagId: String, accessToken: String): Tag {
        val bearer = "Bearer $accessToken"
        return tagApi.likeTagAuthorized(tagId, bearer).toTag()
    }

    override suspend fun deleteLikeAuthorized(tagId: String, accessToken: String) {
        val bearer = "Bearer $accessToken"
        tagApi.deleteLikeFromTagAuthorized(tagId, bearer)
    }

}