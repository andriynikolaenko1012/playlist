package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName

class PageInfoModel {

    @SerializedName("totalResults")
    var totalResults: Int? = null
    @SerializedName("resultsPerPage")
    var resultsPerPage: Int? = null
}