package ru.gozerov.data.tags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.tags.cache.TagStorage
import ru.gozerov.data.tags.remote.TagRemote
import ru.gozerov.data.tags.remote.push.PushRemote
import ru.gozerov.data.utils.ApiConstants.defaultOptions
import ru.gozerov.data.utils.ApiConstants.imageOptions
import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.CreateTagResult
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.models.tags.FilterOption
import ru.gozerov.domain.models.tags.GetFilterOptionResult
import ru.gozerov.domain.models.tags.GetTagDetailsResult
import ru.gozerov.domain.models.tags.GetTagsByOptionAndUserData
import ru.gozerov.domain.models.tags.GetTagsByOptionData
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.models.tags.TagDetails
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagRemote: TagRemote,
    private val pushRemote: PushRemote,
    private val userStorage: UserStorage,
    private val tagStorage: TagStorage
) : TagRepository {

    override suspend fun getTags(): Flow<GetTagsResult> = withContext(Dispatchers.IO) {
        return@withContext flow<GetTagsResult> {
            val token = userStorage.getCurrentAccessToken()
            if (token != null) {
                val response = tagRemote.getTagsAuthorized(token)
                response
                    .onSuccess { tags ->
                        emit(GetTagsResult.Success(tags))
                        tagStorage.insertTags(tags)
                    }
                    .onFailure {
                        loadCacheTags()
                    }
            } else {
                val response = tagRemote.getTags()
                response
                    .onSuccess { tags ->
                        emit(GetTagsResult.Success(tags))
                        tagStorage.insertTags(tags)
                    }
                    .onFailure {
                        loadCacheTags()
                    }
            }
        }
    }

    override suspend fun getTagsByOption(getTagsByOptionData: GetTagsByOptionData): Flow<GetTagsResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<GetTagsResult> {
                val token = userStorage.getCurrentAccessToken()
                if (token != null) {
                    val response = tagRemote.getTagsByOptionAuthorized(
                        token,
                        getTagsByOptionData.defaultOption,
                        getTagsByOptionData.imageOption
                    )
                    response
                        .onSuccess { tags ->
                            emit(GetTagsResult.Success(tags))
                            tagStorage.insertTags(tags)
                        }
                        .onFailure {
                            loadCacheTags()
                        }
                } else {
                    val response = tagRemote.getTagsByOption(
                        getTagsByOptionData.defaultOption,
                        getTagsByOptionData.imageOption
                    )
                    response
                        .onSuccess { tags ->
                            emit(GetTagsResult.Success(tags))
                            tagStorage.insertTags(tags)
                        }
                        .onFailure {
                            loadCacheTags()
                        }
                }
            }
        }

    override suspend fun getTagsByOptionAndUser(getTagsByOptionAndUserData: GetTagsByOptionAndUserData): Flow<GetTagsResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<GetTagsResult> {
                val token = userStorage.getCurrentAccessToken()
                if (token != null) {
                    val response = tagRemote.getTagsByOptionAndUserAuthorized(
                        token,
                        getTagsByOptionAndUserData.username,
                        getTagsByOptionAndUserData.defaultOption,
                        getTagsByOptionAndUserData.imageOption
                    )
                    response
                        .onSuccess { tags ->
                            emit(GetTagsResult.Success(tags))
                            tagStorage.insertTags(tags)
                        }
                        .onFailure {
                            loadCacheTags()
                        }
                } else {
                    val response = tagRemote.getTagsByOptionAndUser(
                        getTagsByOptionAndUserData.username,
                        getTagsByOptionAndUserData.defaultOption,
                        getTagsByOptionAndUserData.imageOption
                    )
                    response
                        .onSuccess { tags ->
                            emit(GetTagsResult.Success(tags))
                            tagStorage.insertTags(tags)
                        }
                        .onFailure {
                            loadCacheTags()
                        }
                }
            }
        }

    override suspend fun getTagDetailsById(id: String): Flow<GetTagDetailsResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<GetTagDetailsResult> {
                val tag = tagStorage.getTagById(id)
                val username = tag.user?.username
                val isSubscribed = username?.let { userStorage.checkSubscription(username) }
                val isLoggedUserAuthor = userStorage.isLoggedUserAuthor(tag)
                emit(GetTagDetailsResult.Success(TagDetails(tag, isLoggedUserAuthor, isSubscribed)))
            }
        }

    override suspend fun getFilterOption(): Flow<GetFilterOptionResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(GetFilterOptionResult.Success(FilterOption(defaultOptions, imageOptions)))
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
                        imageUri = createTagData.imageUri,
                        accessToken = token
                    )
                    response
                        .onSuccess { tag ->
                            val user = userStorage.getUserByToken(token)
                            user?.let {
                                pushRemote.sendPush(it.username)
                            }
                            emit(CreateTagResult.Success(tag))
                        }
                        .onFailure {
                            emit(CreateTagResult.Error)
                        }
                } else {
                    val response = tagRemote.createTag(
                        latitude = createTagData.latitude,
                        longitude = createTagData.longitude,
                        description = createTagData.description,
                        imageUri = createTagData.imageUri
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
                    .onSuccess { tag ->
                        val isLoggedUserAuthor = userStorage.isLoggedUserAuthor(tag)
                        val username = tag.user?.username
                        val isSubscribed = username?.let { userStorage.checkSubscription(username) }
                        emit(
                            LikeTagResult.Success(
                                TagDetails(
                                    tag,
                                    isLoggedUserAuthor,
                                    isSubscribed
                                )
                            )
                        )
                    }
                    .onFailure {
                        emit(LikeTagResult.Error)
                    }
            } ?: emit(LikeTagResult.Error)
        }
    }

    override suspend fun deleteLike(tag: Tag): Flow<DeleteLikeResult> =
        withContext(Dispatchers.IO) {
            return@withContext flow<DeleteLikeResult> {
                val token = userStorage.getCurrentAccessToken()
                token?.let {
                    val response = tagRemote.deleteLikeAuthorized(tag.id, token)
                    response
                        .onSuccess {
                            val newTag = tag.copy(
                                isLiked = false,
                                likes = tag.likes - 1
                            )
                            val isLoggedUserAuthor = userStorage.isLoggedUserAuthor(newTag)
                            val username = tag.user?.username
                            val isSubscribed =
                                username?.let { userStorage.checkSubscription(username) }
                            emit(
                                DeleteLikeResult.Success(
                                    TagDetails(
                                        newTag,
                                        isLoggedUserAuthor,
                                        isSubscribed
                                    )
                                )
                            )
                        }
                        .onFailure {
                            emit(DeleteLikeResult.Error)
                        }
                } ?: emit(DeleteLikeResult.Error)
            }
        }

    private suspend fun FlowCollector<GetTagsResult>.loadCacheTags() {
        val tags = tagStorage.getTags()
        if (tags.isNotEmpty())
            emit(GetTagsResult.Success(tags))
        else
            emit(GetTagsResult.Error)
    }

}