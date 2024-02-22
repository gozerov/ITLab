package ru.gozerov.domain.usecases.tags

import ru.gozerov.domain.models.tags.CreateTagData
import ru.gozerov.domain.models.tags.CreateTagResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class CreateTag @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<CreateTagData, CreateTagResult>() {

    override suspend fun loadData(arg: CreateTagData): CreateTagResult =
        CreateTagResult.Success(tagRepository.createTag(arg))


    override suspend fun onError(e: Exception) {
        _result.emit(CreateTagResult.Error)
    }

}