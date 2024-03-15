package ru.gozerov.data.tags.remote.push

import ru.gozerov.data.tags.remote.models.PushBody
import ru.gozerov.data.tags.remote.models.PushNotification
import javax.inject.Inject

class PushRemoteImpl @Inject constructor(
    private val pushApi: PushApi
) : PushRemote {

    override suspend fun sendPush(username: String) {
        pushApi.sendPush(
            PushBody(
                to = "/topics/$username",
                notification = PushNotification(
                    title = "Пользователь $username добавил метку",
                    body = "Спеши увидеть!"
                )
            )
        )
    }

}