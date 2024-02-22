package ru.gozerov.itlab.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.login.cache.room.UserDatabase

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    fun provideUserDao(@ApplicationContext context: Context) =
        UserDatabase.getInstance(context).getUserDao()

}