package ru.gozerov.presentation.navigation

import androidx.annotation.DrawableRes

sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int? = null
) {
    object Login : Screen("login")
    object ChooseAccount : Screen("choose_account")
    object MainSection : Screen("main_section")

    object TagDetails : Screen("tag_details")

}
