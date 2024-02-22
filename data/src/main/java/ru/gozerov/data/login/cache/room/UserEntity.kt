package ru.gozerov.data.login.cache.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.domain.models.users.User

@Entity(tableName = RoomConstants.USERS_TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val username: String,
    val token: String
) {

    fun toUser(): User = User(username)

}