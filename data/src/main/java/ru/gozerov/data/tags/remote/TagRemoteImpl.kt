package ru.gozerov.data.tags.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.gozerov.data.utils.toTag
import ru.gozerov.domain.models.tags.Tag
import java.io.File
import javax.inject.Inject


class TagRemoteImpl @Inject constructor(
    private val tagApi: TagApi
) : TagRemote {

    override suspend fun getTags(): Result<List<Tag>> {
        return tagApi.getTags().map { list -> list.map { tagResponse -> tagResponse.toTag() } }
    }

    override suspend fun getTagsAuthorized(accessToken: String): Result<List<Tag>> {
        val bearer = "Bearer $accessToken"
        return tagApi.getTagsAuthorized(bearer)
            .map { list -> list.map { tagResponse -> tagResponse.toTag() } }
    }

    override suspend fun createTag(
        latitude: Double,
        longitude: Double,
        description: String,
        imagePath: String?
    ): Result<Tag> {
        val imageFile = imagePath?.let { File(it) }
        var imagePart: MultipartBody.Part? = null
        imageFile?.let {
            val requestBody: RequestBody =
                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody)
        }
        return tagApi.createTag(latitude, longitude, description, imagePart)
            .map { it.toTag() }
    }

    override suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        imagePath: String?,
        accessToken: String
    ): Result<Tag> {
        val bearer = "Bearer $accessToken"
        val imageFile = imagePath?.let { File(it) }
        var imagePart: MultipartBody.Part? = null
        imageFile?.let {
            val requestBody: RequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody)
        }
        return tagApi.createTagAuthorized(bearer, latitude, longitude, description, imagePart)
            .map { it.toTag() }
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