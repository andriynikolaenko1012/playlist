package app.com.playlistfeedreader.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ThinkSoft on 19/12/2017.
 */
@Entity(tableName = "videos")
data class Video(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Int = 0,

        @ColumnInfo(name = "title") var title: String = "",

        @ColumnInfo(name = "description") var description: String = "",

        @ColumnInfo(name = "pltName") var pltName: String = "",

        @ColumnInfo(name = "videoId") var videoId: String = "",

        @ColumnInfo(name = "image") var image: String = ""
)