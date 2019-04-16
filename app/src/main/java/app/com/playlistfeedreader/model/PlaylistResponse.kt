package app.com.playlistfeedreader.model

import com.google.gson.annotations.SerializedName

class PlaylistResponse {

    @SerializedName("kind")
    var kind: String? = null
    @SerializedName("etag")
    var etag: String? = null
    @SerializedName("nextPageToken")
    var nextPageToken: String? = null
    @SerializedName("pageInfo")
    var pageInfo: PageInfoModel? = null
    @SerializedName("items")
    var items: ArrayList<ItemModel>? = null
}