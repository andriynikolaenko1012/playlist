package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ContentDetailsModel : Serializable {

    @SerializedName("videoId")
    var videoId: String? = null
    @SerializedName("videoPublishedAt")
    var videoPublishedAt: String? = null
}