package ru.gozerov.itlab.app

import android.app.Application
import android.content.Context
import ru.gozerov.itlab.di.AppComponent
import ru.gozerov.itlab.di.DaggerAppComponent

class ITLabApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

}

val Context.appComponent: AppComponent
    get() = (applicationContext as ITLabApp).appComponent