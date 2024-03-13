package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagsByOptionAndUserData
import ru.gozerov.domain.models.tags.GetTagsResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTagsByOptionAndUser @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<GetTagsByOptionAndUserData, GetTagsResult>() {

    override suspend fun loadData(arg: GetTagsByOptionAndUserData): Flow<GetTagsResult> =
        tagRepository.getTagsByOptionAndUser(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(GetTagsResult.Error)
    }

}