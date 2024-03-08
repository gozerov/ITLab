package ru.gozerov.data.tags.cache.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.database.DBConstants

@Entity(tableName = DBConstants.TAGS_TABLE_NAME)
data class TagEntity(
    @PrimaryKey
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val image: String?,
    val likes: Int,
    val isLiked: Boolean,
    val username: String?
)