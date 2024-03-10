package ru.gozerov.domain.models.tags

sealed class GetFilterOptionResult {

    class Success(
        val option: FilterOption
    ) : GetFilterOptionResult()

    object Error: GetFilterOptionResult()

}