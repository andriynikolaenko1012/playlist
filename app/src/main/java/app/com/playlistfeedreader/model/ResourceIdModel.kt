package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResourceIdModel : Serializable {

    @SerializedName("videoId")
    var videoId: String? = null
}