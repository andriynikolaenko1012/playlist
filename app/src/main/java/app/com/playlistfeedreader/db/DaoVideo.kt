package app.com.playlistfeedreader.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by ThinkSoft on 19/12/2017.
 */
@Dao
interface DaoVideo {
    @Query("select * from videos")
    fun getAllVideos(): LiveData<List<Video>>

    @Query("delete from videos")
    fun deleteAllVideos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: Video)
}