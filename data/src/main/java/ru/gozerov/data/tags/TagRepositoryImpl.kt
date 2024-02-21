package ru.gozerov.data.tags

import ru.gozerov.domain.models.Tag
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(

) : TagRepository {

    override suspend fun getTags(): List<Tag> {
        TODO("Not yet implemented")
    }

    override suspend fun createTag(latitude: Double, longitude: Double, description: String): Tag {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTag(tagId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun likeTag(tagId: String): Tag {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLike(tagId: String): Boolean {
        TODO("Not yet implemented")
    }

}