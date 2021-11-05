package com.devbea.myusic.playlist.api

import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.model.PlaylistRaw
import retrofit2.http.GET

interface PlaylistAPI {
    @GET("/playlists")
  suspend  fun fetchAllPlaylists(): List<PlaylistRaw>

}
