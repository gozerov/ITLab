package ru.gozerov.itlab.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gozerov.itlab.utils.AppConstants
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [RoomModule::class])
class CacheModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.APP_NAME, Context.MODE_PRIVATE)
    }

}