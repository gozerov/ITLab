package ru.gozerov.data.login.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private var database: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            return database ?: Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                RoomConstants.DB_NAME
            ).build()
        }
    }

}