package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.Tag

interface TagRepository : Repository {

    suspend fun getTags(): List<Tag>

    suspend fun createTag(createTagData: CreateTagData): Tag

    suspend fun deleteTag(tagId: String)

    suspend fun likeTag(tagId: String): Tag

    suspend fun deleteLike(tagId: String)

}