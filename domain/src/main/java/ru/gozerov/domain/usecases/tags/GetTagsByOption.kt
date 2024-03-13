package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagsByOptionData
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTagsByOption @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<GetTagsByOptionData, GetTagsResult>() {

    override suspend fun loadData(arg: GetTagsByOptionData): Flow<GetTagsResult> =
        tagRepository.getTagsByOption(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsResult.Error)
    }

}