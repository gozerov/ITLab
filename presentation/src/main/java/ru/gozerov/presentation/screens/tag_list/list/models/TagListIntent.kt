package ru.gozerov.presentation.screens.tag_list.list.models

sealed class TagListIntent {

    object LoadFilters : TagListIntent()

    class LoadTagsByFilters(
        val defaultOption: String,
        val imageOption: String
    ) : TagListIntent()

    class LoadTagsByFiltersAndUser(
        val username: String,
        val defaultOption: String,
        val imageOption: String
    ) : TagListIntent()

}