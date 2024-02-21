package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Inject

class DeleteTag @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, DeleteTagResult>() {

    override suspend fun loadData(arg: String): DeleteTagResult {
        tagRepository.deleteTag(arg)
        return DeleteTagResult.Success
    }

    override suspend fun onError(e: Exception) {
        _result.emit(DeleteTagResult.Error)
    }

}