package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagByOptionData
import ru.gozerov.domain.models.tags.GetTagsByOptionResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTagsByOption @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<GetTagByOptionData, GetTagsByOptionResult>() {

    override suspend fun loadData(arg: GetTagByOptionData): Flow<GetTagsByOptionResult> =
        tagRepository.getTagsByOption(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsByOptionResult.Error)
    }

}