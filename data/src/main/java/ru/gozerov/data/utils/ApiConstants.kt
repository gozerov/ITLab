package ru.gozerov.data.utils

object ApiConstants {

    const val BASE_URL = "https://maps.rtuitlab.dev/"

    const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"

    const val HTTP_400 = "HTTP 400 "

    val defaultOptions = listOf(
        "Алфавит авторов (от меньшего к большему)",
        "Алфавит авторов (от большего к меньшему)",
        "Количество лайков (от меньшего к большему)",
        "Количество лайков (от большего к меньшему)"
    )

    val imageOptions = listOf("Все", "Только с картинкой")

}