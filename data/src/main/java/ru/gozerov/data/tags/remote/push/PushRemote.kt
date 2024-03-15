package ru.gozerov.data.tags.remote.push

interface PushRemote {

    suspend fun sendPush(username: String)

}