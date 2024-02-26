package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class DeleteLike @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, DeleteLikeResult>() {

    override suspend fun loadData(arg: String): Flow<DeleteLikeResult> =
        tagRepository.deleteLike(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(DeleteLikeResult.Error)
    }

}