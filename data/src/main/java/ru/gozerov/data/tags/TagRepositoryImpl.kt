package ru.gozerov.data.tags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.tags.remote.TagRemote
import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.Tag
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagRemote: TagRemote,
    private val userStorage: UserStorage
) : TagRepository {

    override suspend fun getTags(): List<Tag> = withContext(Dispatchers.IO) {
        return@withContext tagRemote.getTags()
    }

    override suspend fun createTag(createTagData: CreateTagData): Tag =
        withContext(Dispatchers.IO) {
            userStorage.getCurrentAccessToken()?.let { token ->
                return@withContext tagRemote.createTagAuthorized(
                    latitude = createTagData.latitude,
                    longitude = createTagData.longitude,
                    description = createTagData.description,
                    accessToken = token
                )
            } ?: return@withContext tagRemote.createTag(
                createTagData.latitude,
                createTagData.longitude,
                createTagData.description
            )
        }

    override suspend fun deleteTag(tagId: String) = withContext(Dispatchers.IO) {
        val token = userStorage.getCurrentAccessToken() ?: throw NullPointerException("Not Authorized")
        tagRemote.deleteTagAuthorized(tagId, token)
    }

    override suspend fun likeTag(tagId: String): Tag = withContext(Dispatchers.IO) {
        val token = userStorage.getCurrentAccessToken() ?: throw NullPointerException("Not Authorized")
        return@withContext tagRemote.likeTagAuthorized(tagId, token)
    }

    override suspend fun deleteLike(tagId: String) = withContext(Dispatchers.IO) {
        val token = userStorage.getCurrentAccessToken() ?: throw NullPointerException("Not Authorized")
        tagRemote.deleteLikeAuthorized(tagId, token)
    }

}