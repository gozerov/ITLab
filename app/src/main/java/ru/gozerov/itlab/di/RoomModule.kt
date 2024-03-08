package ru.gozerov.itlab.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.database.AppDatabase

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    fun provideUserDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).getUserDao()

    @Provides
    fun provideTagDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).getTagDao()

}