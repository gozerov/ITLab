package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetTagDetailsResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetTagDetails @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, GetTagDetailsResult>() {

    override suspend fun loadData(arg: String): Flow<GetTagDetailsResult> =
        tagRepository.getTagDetailsById(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(GetTagDetailsResult.Error)
    }

}