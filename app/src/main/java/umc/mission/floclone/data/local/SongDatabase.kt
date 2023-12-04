package umc.mission.floclone.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import umc.mission.floclone.data.entities.Album
import umc.mission.floclone.data.entities.Like
import umc.mission.floclone.data.entities.Song
import umc.mission.floclone.data.entities.User

@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 1)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object{
        private var instance: SongDatabase? = null
        fun getInstance(context: Context): SongDatabase?{
            if(instance == null){
                synchronized(SongDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}