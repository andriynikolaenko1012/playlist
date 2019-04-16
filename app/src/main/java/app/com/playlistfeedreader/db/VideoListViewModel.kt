package app.com.playlistfeedreader.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

/**
 * Created by ThinkSoft on 21/12/2017.
 */
class VideoListViewModel(application: Application) : AndroidViewModel(application) {

    var listVideo: LiveData<List<Video>>
    private val appDb: VideoDb

    init {
        appDb = VideoDb.getDataBase(this.getApplication())
        listVideo = appDb.daoContact().getAllContacts()
    }

    fun getListContacts(): LiveData<List<Video>> {
        return listVideo
    }

    fun addContact(video: Video) {
        addAsynTask(appDb).execute(video)
    }


    class addAsynTask(db: VideoDb) : AsyncTask<Video, Void, Void>() {
        private var contactDb = db
        override fun doInBackground(vararg params: Video): Void? {
            contactDb.daoContact().insertContact(params[0])
            return null
        }

    }

}