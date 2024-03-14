package ru.gozerov.data.tags.remote

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.gozerov.data.tags.remote.models.TagResponseBody
import ru.gozerov.data.utils.ApiConstants.defaultOptions
import ru.gozerov.data.utils.ApiConstants.imageOptions
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

    override suspend fun getTagsByOption(
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>> {
        val isReversed = isTagListReversed(defaultOption)
        val defaultFilter = getDefaultFilter(defaultOption)
        val imageFilter = getImageFilter(imageOption)

        return if (imageFilter == null)
            tagApi.getTagsByOrder(defaultFilter).mapByDirection(isReversed)
        else
            tagApi.getTagsByOptions(defaultFilter, imageFilter).mapByDirection(isReversed)
    }

    override suspend fun getTagsByOptionAndUser(
        username: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>> {
        val isReversed = isTagListReversed(defaultOption)
        val defaultFilter = getDefaultFilter(defaultOption)
        val imageFilter = getImageFilter(imageOption)

        return if (imageFilter == null)
            tagApi.getTagsByOrderAndUsername(username, defaultFilter).mapByDirection(isReversed)
        else
            tagApi.getTagsByOptionsAndUsername(username, defaultFilter, imageFilter)
                .mapByDirection(isReversed)
    }

    override suspend fun getTagsAuthorized(accessToken: String): Result<List<Tag>> {
        val bearer = "Bearer $accessToken"
        return tagApi.getTagsAuthorized(bearer)
            .map { list -> list.map { tagResponse -> tagResponse.toTag() } }
    }

    override suspend fun getTagsByOptionAuthorized(
        accessToken: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>> {
        val bearer = "Bearer $accessToken"
        val isReversed = isTagListReversed(defaultOption)
        val defaultFilter = getDefaultFilter(defaultOption)
        val imageFilter = getImageFilter(imageOption)

        return if (imageFilter == null)
            tagApi.getTagsByOrderAuthorized(bearer, defaultFilter)
                .mapByDirection(isReversed)
        else
            tagApi.getTagsByOptionsAuthorized(bearer, defaultFilter, imageFilter)
                .mapByDirection(isReversed)
    }

    override suspend fun getTagsByOptionAndUserAuthorized(
        accessToken: String,
        username: String,
        defaultOption: String,
        imageOption: String
    ): Result<List<Tag>> {
        val bearer = "Bearer $accessToken"
        val isReversed = isTagListReversed(defaultOption)
        val defaultFilter = getDefaultFilter(defaultOption)
        val imageFilter = getImageFilter(imageOption)

        return if (imageFilter == null)
            tagApi.getTagsByOrderAndUsernameAuthorized(bearer, username, defaultFilter)
                .mapByDirection(isReversed)
        else
            tagApi.getTagsByOptionsAndUsernameAuthorized(
                bearer,
                username,
                defaultFilter,
                imageFilter
            )
                .mapByDirection(isReversed)
    }

    override suspend fun createTag(
        latitude: Double,
        longitude: Double,
        description: String,
        imageUri: Uri?
    ): Result<Tag> {
        val latitudeBody = latitude.toString().toRequestBody(MultipartBody.FORM)
        val longitudeBody = longitude.toString().toRequestBody(MultipartBody.FORM)
        val descriptionBody = description.toRequestBody(MultipartBody.FORM)
        return tagApi.createTag(
            latitudeBody,
            longitudeBody,
            descriptionBody,
            getImagePart(imageUri)
        )
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
            inputStream?.copyTo(outputStream)
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        }
        return part
    }

    private fun getDefaultFilter(option: String): String {
        return when (defaultOptions.indexOf(option)) {
            0, 1 -> "user__username"
            2, 3 -> "likes"
            else -> throw IllegalArgumentException("Unknown filter")
        }
    }

    private fun getImageFilter(option: String): String? {
        return when (imageOptions.indexOf(option)) {
            0 -> null
            1 -> ""
            else -> throw IllegalArgumentException("Unknown filter")
        }
    }

    private fun isTagListReversed(defaultOption: String): Boolean {
        return when (defaultOptions.indexOf(defaultOption)) {
            1, 3 -> false
            0, 2 -> true
            else -> throw IllegalArgumentException("Unknown filter")
        }
    }

    private fun Result<List<TagResponseBody>>.mapByDirection(
        isReversed: Boolean,
    ): Result<List<Tag>> {
        return this.map { list ->
            if (isReversed)
                list
                    .map { tagResponse -> tagResponse.toTag() }
            else
                list
                    .map { tagResponse -> tagResponse.toTag() }
                    .reversed()
        }
    }

}