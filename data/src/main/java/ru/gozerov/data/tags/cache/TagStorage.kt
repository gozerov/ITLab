package ru.gozerov.data.tags.cache

import ru.gozerov.domain.models.tags.Tag

interface TagStorage {

    suspend fun getTags(): List<Tag>

    suspend fun insertTags(tags: List<Tag>)

    suspend fun getTagById(id: String): Tag

}