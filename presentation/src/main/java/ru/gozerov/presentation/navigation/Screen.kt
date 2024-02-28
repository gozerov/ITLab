package ru.gozerov.presentation.navigation

import androidx.annotation.DrawableRes

sealed class Screen(
    val route: String,
    val label: String? = null,
    @DrawableRes val icon: Int? = null
) {
    object Login : Screen("login")
}

/*
val items = listOf(
    Screen.News,
    Screen.Calendar,
    Screen.Services,
    Screen.Profile,
)*/
