package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.GetFilterOptionResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class GetFilterOptions @Inject constructor(
    private val tagRepository: TagRepository
): UseCase<Unit, GetFilterOptionResult>() {

    override suspend fun loadData(arg: Unit): Flow<GetFilterOptionResult> =
        tagRepository.getFilterOption()

    override suspend fun onError(e: Exception) {
        _result.emit(GetFilterOptionResult.Error)
    }
}