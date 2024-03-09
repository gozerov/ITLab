package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTagsByUser @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, GetTagsResult>() {

    override suspend fun loadData(arg: String): Flow<GetTagsResult> =
        tagRepository.getTagsByUser(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsResult.Error)
    }

}