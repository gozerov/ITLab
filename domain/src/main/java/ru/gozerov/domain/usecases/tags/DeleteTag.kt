package ru.gozerov.domain.usecases.tags

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.tags.DeleteTagResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class DeleteTag @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, DeleteTagResult>() {

    override suspend fun loadData(arg: String): Flow<DeleteTagResult> =
        tagRepository.deleteTag(arg)

    override suspend fun onError(e: Exception) {
        _result.emit(DeleteTagResult.Error)
    }

}