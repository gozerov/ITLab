package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.tags.DeleteLikeResult
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class DeleteLike @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, DeleteLikeResult>() {

    override suspend fun loadData(arg: String): DeleteLikeResult {
        tagRepository.deleteLike(arg)
        return DeleteLikeResult.Success
    }

    override suspend fun onError(e: Exception) {
        _result.emit(DeleteLikeResult.Error)
    }

}