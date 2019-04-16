package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResourceIdModel : Serializable {

    @SerializedName("kind")
    var kind: String? = null
    @SerializedName("videoId")
    var videoId: String? = null
}