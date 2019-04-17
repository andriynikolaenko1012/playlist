package app.com.playlistfeedreader.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.com.playlistfeedreader.R
import app.com.playlistfeedreader.activity.PlayerActivity
import app.com.playlistfeedreader.adapter.PlaylistAdapter
import app.com.playlistfeedreader.adapter.PlaylistDBAdapter
import app.com.playlistfeedreader.db.Video
import app.com.playlistfeedreader.db.VideoDb
import app.com.playlistfeedreader.db.VideoListViewModel
import app.com.playlistfeedreader.model.ItemModel
import app.com.playlistfeedreader.model.PlaylistResponse
import app.com.playlistfeedreader.utils.SeparatorDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.playlist_fragment.*
import org.jetbrains.anko.support.v4.toast


class PlaylistFragment : BaseFragment() {

    private lateinit var adapter: PlaylistAdapter
    private lateinit var adapterDB: PlaylistDBAdapter
    private var disposable: Disposable? = null
    private var newPageToken: String? = null
    private var list = arrayListOf<ItemModel>()

    private var viewModel: VideoListViewModel? = null

    private var db: VideoDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.playlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = VideoDb.getDataBase(context!!)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)

        adapter = PlaylistAdapter{

            if (isNetworkAvailable(context!!)){
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("object_model", it)
                startActivity(intent)

            } else {
                fetchListFromDB()
            }
        }

        val decoration = SeparatorDecoration(context!!,
            ContextCompat.getColor(context!!, R.color.colorSeparator), 1f)
        rvItems?.addItemDecoration(decoration)
        rvItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvItems.hasFixedSize()
        rvItems.adapter = adapter

        fetchList(arguments?.getString(ARG_PLAYLIST_ID)!!)

        swipyrefreshlayout.setOnRefreshListener { direction ->
            refreshList(arguments?.getString(ARG_PLAYLIST_ID)!!, newPageToken!!)
        }

        adapterDB = PlaylistDBAdapter()

        rvItemsDB?.addItemDecoration(decoration)
        rvItemsDB.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvItemsDB.hasFixedSize()
        rvItemsDB.adapter = adapterDB

        swipyrefreshlayoutDB.setOnRefreshListener { direction ->
            fetchList(arguments?.getString(ARG_PLAYLIST_ID)!!)
        }
    }

    private fun fetchList(chanelId: String){

        if (isNetworkAvailable(context!!)){

            swipyrefreshlayout.visibility = View.VISIBLE
            swipyrefreshlayoutDB.visibility = View.GONE

            showProgress()

            disposable?.dispose()
            val part = "id,contentDetails,snippet"
            val key = getString(R.string.youtube_api_key)
            disposable = apiService.value.getPlaylistItems(part, chanelId, key, 20, "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: PlaylistResponse ->

                    newPageToken = t.nextPageToken!!
                    list = t.items!!
                    adapter.setList(list)
                    stopProgress()

                }, { e ->
                    e.printStackTrace()
                    toast(e.localizedMessage)
                })
        } else {
            fetchListFromDB()
        }

        swipyrefreshlayoutDB?.isRefreshing = false
    }

    private fun fetchListFromDB(){

        toast("Connection lost. Please, check internet connection and try again.")
        swipyrefreshlayout.visibility = View.GONE
        swipyrefreshlayoutDB.visibility = View.VISIBLE

        viewModel!!.getListVideos().observe(this, Observer { videos ->

            val lst = arrayListOf<Video>()

            for (i in 0 until videos!!.size){
                if (videos[i].pltName == arguments?.getString(ARG_PLAYLIST_ID)!!){
                    lst.add(videos[i])
                }
            }

            adapterDB.setList(lst)
        })
    }

    private fun refreshList(chanelId: String, pageToken: String){

        if (isNetworkAvailable(context!!)){

            disposable?.dispose()
            val part = "id,contentDetails,snippet"
            val key = getString(R.string.youtube_api_key)
            disposable = apiService.value.getPlaylistItems(part, chanelId, key, 20, pageToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: PlaylistResponse ->

                    if (t.nextPageToken != null){
                        newPageToken = t.nextPageToken!!

                        list.addAll(t.items!!)
                        adapter.setList(list)
                    }

                    swipyrefreshlayout.isRefreshing = false
                    swipyrefreshlayoutDB.isRefreshing = false
                }, { e ->
                    e.printStackTrace()
                    toast(e.localizedMessage)
                })
        } else {
            fetchListFromDB()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    companion object {

        val ARG_PLAYLIST_ID = "arg_item"

        fun newInstance(name: String): PlaylistFragment {
            val args = Bundle()
            args.putString(ARG_PLAYLIST_ID, name)
            val fragment = PlaylistFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
