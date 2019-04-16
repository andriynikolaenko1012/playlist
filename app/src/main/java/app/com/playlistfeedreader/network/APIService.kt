package app.com.playlistfeedreader.network

import app.com.playlistfeedreader.model.PlaylistResponse
import io.reactivex.Observable
import retrofit2.http.*
import retrofit2.http.POST


interface APIService {

    @GET("/youtube/v3/playlistItems")
    fun getPlaylistItems(@Query("part") part: String,
                         @Query("playlistId") playlistId: String,
                         @Query("key") key: String,
                         @Query("maxResults") maxResults: Int,
                         @Query("pageToken") pageToken: String): Observable<PlaylistResponse>
}

