package ru.gozerov.data.tags.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.gozerov.data.tags.remote.models.TagResponseBody

interface TagApi {

    @GET("api/tags/")
    suspend fun getTags(): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptions(
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrder(
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptionsAndUsername(
        @Query("user__username") username: String,
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrderAndUsername(
        @Query("user__username") username: String,
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsAuthorized(
        @Header("Authorization") bearer: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptionsAuthorized(
        @Header("Authorization") bearer: String,
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrderAuthorized(
        @Header("Authorization") bearer: String,
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptionsAndUsernameAuthorized(
        @Header("Authorization") bearer: String,
        @Query("user__username") username: String,
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrderAndUsernameAuthorized(
        @Header("Authorization") bearer: String,
        @Query("user__username") username: String,
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @Multipart
    @POST("api/tags/")
    suspend fun createTag(
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Result<TagResponseBody>

    @Multipart
    @POST("api/tags/")
    suspend fun createTagAuthorized(
        @Header("Authorization") bearer: String,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Result<TagResponseBody>

    @DELETE("api/tags/{tagId}")
    suspend fun deleteTagAuthorized(
        @Path("tagId") tagId: String,
        @Header("Authorization") bearer: String
    ): Result<String>

    @POST("api/tags/{tagId}/likes")
    suspend fun likeTagAuthorized(
        @Path("tagId") tagId: String,
        @Header("Authorization") bearer: String
    ): Result<TagResponseBody>

    @DELETE("api/tags/{tagId}/likes")
    suspend fun deleteLikeFromTagAuthorized(
        @Path("tagId") tagId: String,
        @Header("Authorization") bearer: String
    ): Result<String>

}