package ru.gozerov.data.login.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

}