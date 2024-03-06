package ru.gozerov.data.tags.remote

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.gozerov.data.utils.toTag
import ru.gozerov.domain.models.tags.Tag
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class TagRemoteImpl @Inject constructor(
    private val tagApi: TagApi,
    @ApplicationContext private val context: Context
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
        imageUri: Uri?
    ): Result<Tag> {
        return tagApi.createTag(latitude, longitude, description, getImagePart(imageUri))
            .map { it.toTag() }
    }

    override suspend fun createTagAuthorized(
        latitude: Double,
        longitude: Double,
        description: String,
        imageUri: Uri?,
        accessToken: String
    ): Result<Tag> {
        val bearer = "Bearer $accessToken"
        val latitudeBody = latitude.toString().toRequestBody(MultipartBody.FORM)
        val longitudeBody = longitude.toString().toRequestBody(MultipartBody.FORM)
        val descriptionBody = description.toRequestBody(MultipartBody.FORM)
        return tagApi.createTagAuthorized(
            bearer,
            latitudeBody,
            longitudeBody,
            descriptionBody,
            getImagePart(imageUri)
        )
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

    private fun getImagePart(imageUri: Uri?): MultipartBody.Part? {
        var part: MultipartBody.Part? = null
        imageUri?.let { uri ->
            val filesDir = context.filesDir
            val file = File(filesDir, "image.png")
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        }
        return part
    }

}