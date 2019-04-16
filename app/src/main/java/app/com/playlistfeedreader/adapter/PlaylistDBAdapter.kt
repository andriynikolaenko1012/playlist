package app.com.playlistfeedreader.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.com.playlistfeedreader.R
import app.com.playlistfeedreader.db.Video
import app.com.playlistfeedreader.model.ItemModel
import com.bumptech.glide.Glide
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class PlaylistDBAdapter : RecyclerView.Adapter<PlaylistDBAdapter.MyViewHolder>() {

    private var dataList: ArrayList<Video> = arrayListOf()

    fun setList(list: ArrayList<Video>) {
        dataList = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.find(R.id.thumbnail)
        var title: TextView = view.find(R.id.title)
        var description: TextView = view.find(R.id.description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]

        Glide.with(holder.itemView.context).load(item.image).into(holder.thumbnail)

        item.title.let {  holder.title.text = it }

        val fullDescription = item.description
        if (fullDescription.contains("\n")){
            val newStr = fullDescription?.substring(0, fullDescription.indexOf("\n"))
            newStr.let { holder.description.text = it }
        } else {
            item.description.let { holder.description.text = it  }
        }

        holder.itemView.setOnClickListener {
           holder.itemView.context.toast("Can't reproduce in offline mode")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
