package com.devbea.myusic.playlist.service

import com.devbea.myusic.common.api.PlaylistAPI
import com.devbea.myusic.playlist.model.PlaylistRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistService @Inject constructor(private val api: PlaylistAPI) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            val data = api.fetchAllPlaylists()
            emit(Result.success(data))
        }.catch {
            emit(Result.failure<List<PlaylistRaw>>(it))
        }
    }


}
