package ru.gozerov.data.tags.remote.push

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.gozerov.data.tags.remote.models.PushBody
import ru.gozerov.data.utils.FirebaseConstants

interface PushApi {

    @POST("fcm/send")
    suspend fun sendPush(
        @Body pushBody: PushBody,
        @Header("Authorization") key: String = "key=${FirebaseConstants.FIREBASE_KEY}"
    )

}