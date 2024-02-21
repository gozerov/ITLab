package ru.gozerov.itlab.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.gozerov.itlab.utils.AppConstants
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.APP_NAME, Context.MODE_PRIVATE)
    }

}