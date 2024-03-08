package ru.gozerov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gozerov.data.login.cache.room.UserDao
import ru.gozerov.data.login.cache.room.UserEntity
import ru.gozerov.data.tags.cache.room.TagDao
import ru.gozerov.data.tags.cache.room.TagEntity

@Database(entities = [UserEntity::class, TagEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getTagDao(): TagDao

    companion object {

        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return database ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DBConstants.DB_NAME
            ).build()
        }
    }

}