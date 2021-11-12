package com.devbea.myusic.playlistDetails.service

import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor() {
    fun fetchPlaylistWithId(id: String): Flow<Result<PlaylistDetailsRaw>> {
        TODO()
    }

}
