package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class GetTags @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<Unit, GetTagsResult>() {

    override suspend fun loadData(arg: Unit): GetTagsResult =
        GetTagsResult.Success(tagRepository.getTags())


    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsResult.Error)
    }

}