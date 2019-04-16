package app.com.playlistfeedreader.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.com.playlistfeedreader.R
import app.com.playlistfeedreader.db.Video
import app.com.playlistfeedreader.db.VideoDb
import app.com.playlistfeedreader.db.VideoListViewModel
import app.com.playlistfeedreader.db.DaoVideo
import app.com.playlistfeedreader.model.ItemModel
import com.jaedongchicken.ytplayer.YoutubePlayerView
import com.jaedongchicken.ytplayer.model.PlaybackQuality
import com.jaedongchicken.ytplayer.model.YTParams
import kotlinx.android.synthetic.main.player_activity.*


class PlayerActivity : AppCompatActivity() {

    private var daoVideo: DaoVideo? = null
    private var viewModel: VideoListViewModel? = null
    private var video: Video? = null

    private var model: ItemModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

        var db: VideoDb = VideoDb.getDataBase(this)
        daoVideo = db.daoContact()
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)


        val intent = intent
        model = intent.getSerializableExtra("object_model") as ItemModel

        val params = YTParams()
        params.playbackQuality = PlaybackQuality.highres
        params.controls = 0

        youtubePlayerView.setAutoPlayerHeight(this)
        youtubePlayerView.initialize(model?.snippet?.resourceId?.videoId, object : YoutubePlayerView.YouTubeListener {

            override fun onReady() {

            }

            override fun onStateChange(state: YoutubePlayerView.STATE) {
                if (state == YoutubePlayerView.STATE.PLAYING){
                    saveVideo()
                }
            }

            override fun onPlaybackQualityChange(arg: String) {}

            override fun onPlaybackRateChange(arg: String) {}

            override fun onError(error: String) {}

            override fun onApiChange(arg: String) {}

            override fun onCurrentSecond(second: Double) {

            }

            override fun onDuration(duration: Double) {

            }

            override fun logs(log: String) {

            }
        })

        youtubePlayerView.pause()
        youtubePlayerView.play()
    }

    override fun onPause() {
        super.onPause()
        youtubePlayerView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerView.destroy()
    }

    private fun saveVideo() {
        val image = model?.snippet?.thumbnails?.standard?.url
        val pltName = model?.snippet?.channelId
        val videoId = model?.snippet?.resourceId?.videoId
        val title = model?.snippet?.title
        val description = model?.snippet?.description

        val contact = Video(0, title!!, description!!, pltName!!, videoId!!, image!!)
        viewModel!!.addContact(contact)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
