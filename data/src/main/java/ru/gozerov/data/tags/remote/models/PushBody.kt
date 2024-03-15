package ru.gozerov.data.tags.remote.models

data class PushBody(
    val to: String,
    val notification: PushNotification
)
