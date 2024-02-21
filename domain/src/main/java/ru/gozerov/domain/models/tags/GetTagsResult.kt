package ru.gozerov.domain.models.tags

sealed class GetTagsResult {

    data class Success(
        val tags: List<Tag>
    ): GetTagsResult()

    data object Error: GetTagsResult()

}