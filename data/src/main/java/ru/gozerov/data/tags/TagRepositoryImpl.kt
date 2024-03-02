package ru.gozerov.data.tags

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.tags.remote.TagRemote
import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.CreateTagResult
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagRemote: TagRemote,
    private val userStorage: UserStorage
) : TagRepository {

    override suspend fun getTags(): Flow<GetTagsResult> = withContext(Dispatchers.IO) {
        return@withContext flow<GetTagsResult> {
            val response = tagRemote.getTags()
            response
                .onSuccess {
                    emit(GetTagsResult.Success(it))
                }
                .onFailure {
                    Log.e("AA", it.message.toString())
                    emit(GetTagsResult.Error)
                }
        }
    }

    override suspend fun createTag(createTagData: CreateTagData): Flow<CreateTagResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<CreateTagResult> {
                val token = userStorage.getCurrentAccessToken()
                if (token != null) {
                    val response = tagRemote.createTagAuthorized(
                        latitude = createTagData.latitude,
                        longitude = createTagData.longitude,
                        description = createTagData.description,
                        accessToken = token
                    )
                    response
                        .onSuccess { tag ->
                            emit(CreateTagResult.Success(tag))
                        }
                        .onFailure {
                            emit(CreateTagResult.Error)
                        }
                }
                else {
                    val response = tagRemote.createTag(
                        createTagData.latitude,
                        createTagData.longitude,
                        createTagData.description
                    )
                    response
                        .onSuccess { tag ->
                            emit(CreateTagResult.Success(tag))
                        }
                        .onFailure {
                            emit(CreateTagResult.Error)
                        }
                }
            }
        }

    override suspend fun deleteTag(tagId: String): Flow<DeleteTagResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<DeleteTagResult> {
                val token = userStorage.getCurrentAccessToken()
                token?.let {
                    val response = tagRemote.deleteTagAuthorized(tagId, token)
                    response
                        .onSuccess {
                            emit(DeleteTagResult.Success)
                        }
                        .onFailure {
                            emit(DeleteTagResult.Error)
                        }
                } ?: emit(DeleteTagResult.Error)
            }
        }

    override suspend fun likeTag(tagId: String): Flow<LikeTagResult> = withContext(Dispatchers.IO) {
        return@withContext flow<LikeTagResult> {
            val token = userStorage.getCurrentAccessToken()
            token?.let {
                val response = tagRemote.likeTagAuthorized(tagId, token)
                response
                    .onSuccess {
                        emit(LikeTagResult.Success(it))
                    }
                    .onFailure {
                        emit(LikeTagResult.Error)
                    }
            } ?: emit(LikeTagResult.Error)
        }
    }

    override suspend fun deleteLike(tagId: String): Flow<DeleteLikeResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<DeleteLikeResult> {
                val token = userStorage.getCurrentAccessToken()
                token?.let {
                    val response = tagRemote.deleteLikeAuthorized(tagId, token)
                    response
                        .onSuccess {
                            emit(DeleteLikeResult.Success)
                        }
                        .onFailure {
                            emit(DeleteLikeResult.Error)
                        }
                } ?: emit(DeleteLikeResult.Error)
            }
        }

}