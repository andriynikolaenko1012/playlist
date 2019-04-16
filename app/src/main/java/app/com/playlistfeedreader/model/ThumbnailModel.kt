package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ThumbnailModel : Serializable {

    @SerializedName("standard")
    var standard: StandartModel? = null
}