package ru.gozerov.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.CreateTagResult
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.models.tags.GetFilterOptionResult
import ru.gozerov.domain.models.tags.GetTagByOptionData
import ru.gozerov.domain.models.tags.GetTagDetailsResult
import ru.gozerov.domain.models.tags.GetTagsByOptionResult
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.models.tags.Tag

interface TagRepository : Repository {

    suspend fun getTags(): Flow<GetTagsResult>

    suspend fun getTagsByOption(getTagByOptionData: GetTagByOptionData): Flow<GetTagsByOptionResult>

    suspend fun getTagsByUser(username: String): Flow<GetTagsResult>

    suspend fun getTagDetailsById(id: String): Flow<GetTagDetailsResult>

    suspend fun getFilterOption(): Flow<GetFilterOptionResult>

    suspend fun createTag(createTagData: CreateTagData): Flow<CreateTagResult>

    suspend fun deleteTag(tagId: String): Flow<DeleteTagResult>

    suspend fun likeTag(tagId: String): Flow<LikeTagResult>

    suspend fun deleteLike(tag: Tag): Flow<DeleteLikeResult>

}