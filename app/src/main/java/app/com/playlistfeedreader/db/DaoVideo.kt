package app.com.playlistfeedreader.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by ThinkSoft on 19/12/2017.
 */
@Dao
interface DaoVideo {
    @Query("select * from videos")
    fun getAllContacts(): LiveData<List<Video>>

    @Query("delete from videos")
    fun deleteAllContacts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(video: Video)
}