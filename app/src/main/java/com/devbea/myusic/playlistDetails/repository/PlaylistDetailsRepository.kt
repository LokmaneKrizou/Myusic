package com.devbea.myusic.playlistDetails.repository

import com.devbea.myusic.playlistDetails.model.PlaylistDetails
import com.devbea.myusic.playlistDetails.service.PlaylistDetailsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistDetailsRepository @Inject constructor(
    private val playlistDetailsService: PlaylistDetailsService,
    private val mapper: PlaylistDetailsMapper
) {
    suspend fun getPlaylistWithId(id: String): Flow<Result<PlaylistDetails>> {
        return playlistDetailsService.fetchPlaylistWithId(id).map { it ->
            if (it.isSuccess) {
                mapper.invoke(it.getOrNull())?.let { Result.success(it) }
                    ?: run { Result.failure(RuntimeException(DEFAULT_ERROR)) }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }
companion object{
    private const val DEFAULT_ERROR="Something went Wrong"
}
}
