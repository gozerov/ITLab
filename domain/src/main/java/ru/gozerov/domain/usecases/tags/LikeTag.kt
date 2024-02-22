package ru.gozerov.domain.usecases.tags

import ru.gozerov.domain.models.tags.LikeTagResult
import ru.gozerov.domain.repositories.TagRepository
import ru.gozerov.domain.usecases.UseCase
import javax.inject.Inject

class LikeTag @Inject constructor(
    private val tagRepository: TagRepository
) : UseCase<String, LikeTagResult>() {

    override suspend fun loadData(arg: String): LikeTagResult =
        LikeTagResult.Success(tagRepository.likeTag(arg))

    override suspend fun onError(e: Exception) {
        _result.emit(LikeTagResult.Error)
    }

}