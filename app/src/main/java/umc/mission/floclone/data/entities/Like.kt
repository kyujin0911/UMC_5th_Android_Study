package umc.mission.floclone.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeTable")
data class Like(
    var userId: String,
    var albumId: Int
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
