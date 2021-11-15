package com.devbea.myusic.playlistDetails.service

import com.devbea.myusic.common.api.PlaylistAPI
import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(private val playlistAPI: PlaylistAPI) {
    suspend fun fetchPlaylistWithId(id: String): Flow<Result<PlaylistDetailsRaw>> {
        return flow {
            val data=playlistAPI.fetchPlaylistById(id)
            emit(Result.success(data))
        }.catch {
            emit(Result.failure<PlaylistDetailsRaw>(it))
        }
    }

}
