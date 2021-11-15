package com.devbea.myusic.common.api

import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.model.PlaylistRaw
import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistAPI {
    @GET("/playlists")
  suspend  fun fetchAllPlaylists(): List<PlaylistRaw>

    @GET("/playlist/{id}")
   suspend fun fetchPlaylistById(@Path("id")id: String):PlaylistDetailsRaw

}
