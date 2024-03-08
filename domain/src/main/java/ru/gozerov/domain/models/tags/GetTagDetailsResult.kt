package ru.gozerov.domain.models.tags

sealed class GetTagDetailsResult {

    class Success(
        val tagDetails: TagDetails
    ) : GetTagDetailsResult()

    object Error : GetTagDetailsResult()

}