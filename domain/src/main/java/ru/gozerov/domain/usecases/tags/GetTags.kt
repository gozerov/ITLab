package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTags @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<Unit, GetTagsResult>() {

    override suspend fun loadData(arg: Unit): Flow<GetTagsResult> =
        tagRepository.getTags()


    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsResult.Error)
    }

}