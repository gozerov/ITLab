package ru.gozerov.data.tags.cache

import ru.gozerov.data.tags.cache.room.TagDao
import ru.gozerov.data.utils.toTag
import ru.gozerov.data.utils.toTagEntity
import ru.gozerov.domain.models.tags.Tag
import javax.inject.Inject

class TagStorageImpl @Inject constructor(
    private val tagDao: TagDao
) : TagStorage {

    override suspend fun getTags(): List<Tag> {
        return tagDao.getTags().map { it.toTag() }
    }

    override suspend fun insertTags(tags: List<Tag>) {
        tagDao.addTags(tags.map { it.toTagEntity() })
    }

    override suspend fun getTagById(id: String): Tag {
        return tagDao.getTagById(id).toTag()
    }

}