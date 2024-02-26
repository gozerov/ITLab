package ru.gozerov.data.tags.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.gozerov.data.tags.remote.models.CreateTagRequestBody
import ru.gozerov.data.tags.remote.models.TagResponseBody

interface TagApi {

    @GET("api/tags")
    suspend fun getTags(): Result<List<TagResponseBody>>

    @POST("api/tags")
    suspend fun createTag(@Body requestBody: CreateTagRequestBody): Result<TagResponseBody>

    @POST("api/tags")
    suspend fun createTagAuthorized(
        @Body requestBody: CreateTagRequestBody,
        @Header("Authorization") bearer: String
    ): Result<TagResponseBody>

    @DELETE("api/tags/{tagId}")
    suspend fun deleteTagAuthorized(
        tagId: String,
        @Header("Authorization") bearer: String
    ): Result<String>

    @POST("api/tags/{tagId}/likes")
    suspend fun likeTagAuthorized(
        tagId: String,
        @Header("Authorization") bearer: String
    ): Result<TagResponseBody>

    @DELETE("api/tags/{tagId}/likes")
    suspend fun deleteLikeFromTagAuthorized(
        tagId: String,
        @Header("Authorization") bearer: String
    ): Result<String>

}