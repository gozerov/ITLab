package ru.gozerov.data.tags.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    suspend fun getTagsByUsername(
        @Query("user__username") username: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptions(
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrderOption(
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsAuthorized(
        @Header("Authorization") bearer: String
    ): Result<List<TagResponseBody>>


    @GET("api/tags/")
    suspend fun getTagsByUsernameAuthorized(
        @Header("Authorization") bearer: String,
        @Query("user__username") username: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOptionsAuthorized(
        @Header("Authorization") bearer: String,
        @Query("order_by") defaultOption: String,
        @Query("image__neq") imageOption: String
    ): Result<List<TagResponseBody>>

    @GET("api/tags/")
    suspend fun getTagsByOrderOptionAuthorized(
        @Header("Authorization") bearer: String,
        @Query("order_by") defaultOption: String
    ): Result<List<TagResponseBody>>

    @Multipart
    @FormUrlEncoded
    @POST("api/tags/")
    suspend fun createTag(
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double,
        @Field("description") description: String,
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