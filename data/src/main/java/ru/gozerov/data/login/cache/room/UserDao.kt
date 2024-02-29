package ru.gozerov.data.login.cache.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByName(username: String): UserEntity

    @Query("DELETE FROM users WHERE username = :name")
    suspend fun deleteUserByName(name: String)

}