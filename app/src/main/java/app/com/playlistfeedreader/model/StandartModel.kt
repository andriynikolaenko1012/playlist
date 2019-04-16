package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StandartModel : Serializable {
    @SerializedName("url")
    var url: String? = null
}