package com.devbea.myusic.common.api

import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.model.PlaylistRaw
import retrofit2.http.GET

interface PlaylistAPI {
    @GET("/playlists")
  suspend  fun fetchAllPlaylists(): List<PlaylistRaw>

}
