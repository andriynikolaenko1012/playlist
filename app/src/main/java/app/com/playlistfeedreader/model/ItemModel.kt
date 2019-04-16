package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemModel : Serializable{

    @SerializedName("kind")
    var kind: String? = null
    @SerializedName("etag")
    var etag: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("snippet")
    var snippet: SnippetModel? = null
    @SerializedName("contentDetails")
    var contentDetails: ContentDetailsModel? = null
}