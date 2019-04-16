package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SnippetModel : Serializable {

    @SerializedName("publishedAt")
    var publishedAt: String? = null
    @SerializedName("channelId")
    var channelId: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("thumbnails")
    var thumbnails: ThumbnailModel? = null
    @SerializedName("channelTitle")
    var channelTitle: String? = null
    @SerializedName("playlistId")
    var playlistId: String? = null
    @SerializedName("position")
    var position: Int? = null
    @SerializedName("resourceId")
    var resourceId: ResourceIdModel? = null
}