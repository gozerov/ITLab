package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.Tag

interface TagRepository: Repository {

    suspend fun getTags(): List<Tag>

    suspend fun createTag(latitude: Double, longitude: Double, description: String): Tag

    suspend fun deleteTag(tagId: String): Boolean

    suspend fun likeTag(tagId: String): Tag

    suspend fun deleteLike(tagId: String): Boolean

}