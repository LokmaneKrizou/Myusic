package com.devbea.myusic.playlistDetails.repository

import com.devbea.myusic.playlistDetails.model.PlaylistDetails
import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import javax.inject.Inject

class PlaylistDetailsMapper @Inject constructor() :
    Function1<PlaylistDetailsRaw?, PlaylistDetails?> {
    override fun invoke(playlistRaw: PlaylistDetailsRaw?): PlaylistDetails? {
        var playlist: PlaylistDetails? = null
        playlistRaw?.apply {
            playlist = PlaylistDetails(id, name, details)
        } ?: run {
            playlist = null
        }
        return playlist
    }

}
