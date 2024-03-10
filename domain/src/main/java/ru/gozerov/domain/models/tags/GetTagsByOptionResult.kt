package ru.gozerov.domain.models.tags

sealed class GetTagsByOptionResult {

    class Success(
        val tags: List<Tag>
    ): GetTagsByOptionResult()

    object Error: GetTagsByOptionResult()

}