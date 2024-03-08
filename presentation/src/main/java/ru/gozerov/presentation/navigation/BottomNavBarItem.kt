package ru.gozerov.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.gozerov.presentation.R

sealed class BottomNavBarItem(
    val route: String,
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int
) {
    object TagMap : BottomNavBarItem("map", R.string.tag_map, R.drawable.ic_map_24)
    object TagListFlow : BottomNavBarItem("list_flow", R.string.tag_list, R.drawable.ic_list_24)
    object Account : BottomNavBarItem("account", R.string.account, R.drawable.ic_account_24)
}

val bottomNavBarItems =
    listOf(BottomNavBarItem.TagMap, BottomNavBarItem.TagListFlow, BottomNavBarItem.Account)