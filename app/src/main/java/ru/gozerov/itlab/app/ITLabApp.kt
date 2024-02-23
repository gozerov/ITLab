package ru.gozerov.itlab.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ITLabApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("59ce5676-53d0-42a9-b7d9-7516928dd5a0")
    }

}