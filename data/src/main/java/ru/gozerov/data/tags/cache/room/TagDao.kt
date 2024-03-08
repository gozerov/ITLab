package ru.gozerov.data.tags.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TagDao {

    @Query("SELECT * FROM tags")
    suspend fun getTags(): List<TagEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTags(tags: List<TagEntity>)

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: String): TagEntity

}