package ru.gozerov.domain.models.tags

sealed class GetTagsResult {

    class Success(
        val tags: List<Tag>
    ): GetTagsResult()

    object Error: GetTagsResult()

}