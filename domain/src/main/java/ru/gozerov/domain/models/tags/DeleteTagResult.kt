package ru.gozerov.domain.models.tags

sealed class DeleteTagResult {

    data object Success: DeleteTagResult()

    data object Error: DeleteTagResult()

}
