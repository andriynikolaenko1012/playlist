package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemModel : Serializable{

    @SerializedName("snippet")
    var snippet: SnippetModel? = null
}