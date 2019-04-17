package app.com.playlistfeedreader.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by ThinkSoft on 19/12/2017.
 */
@Database(entities = [(Video::class)], version = 1, exportSchema = false)
abstract class VideoDb : RoomDatabase() {
    companion object {
        private var INSTANCE: VideoDb? = null
        fun getDataBase(context: Context): VideoDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, VideoDb::class.java, "videos-db")
                        .allowMainThreadQueries().build()
            }
            return INSTANCE as VideoDb
        }
    }

    abstract fun daoVideo(): DaoVideo
}