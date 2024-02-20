package ru.gozerov.itlab.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Builder
import ru.gozerov.itlab.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }

}