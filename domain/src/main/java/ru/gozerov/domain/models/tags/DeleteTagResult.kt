package ru.gozerov.domain.models.tags

sealed class DeleteTagResult {

    object Success: DeleteTagResult()

    object Error: DeleteTagResult()

}
