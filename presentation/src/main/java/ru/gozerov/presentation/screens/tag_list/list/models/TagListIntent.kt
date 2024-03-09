package ru.gozerov.presentation.screens.tag_list.list.models

sealed class TagListIntent {

    object LoadTags : TagListIntent()

    class GetTagsByUser(val username: String) : TagListIntent()

}