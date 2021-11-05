package com.devbea.myusic.playlist.repository

import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.model.PlaylistRaw
import com.devbea.myusic.playlist.service.PlaylistService
import com.devbea.myusic.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.internal.verification.Times
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlaylistRepositoryShould : BaseUnitTest() {
    private lateinit var repository: PlaylistRepository
    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something when south")

    @Test
    fun `get Play lists From Service`() = runBlockingTest {
        repository = mockSuccessfulCase()
        repository.getPlaylists()
        verify(service, Times(1)).fetchPlaylists()
    }

    @Test
    fun `emit Play lists From Service`() = runBlockingTest {
        repository = mockSuccessfulCase()
        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun `delegate playlist mapping to PlaylistMapper`() = runBlockingTest {
        repository = mockSuccessfulCase()
        repository.getPlaylists().first()
        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    @Test
    fun `emit error from Service when receive error`() = runBlockingTest {
        repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(
                Result.failure<List<PlaylistRaw>>(
                    exception
                )
            )
        })

        return PlaylistRepository(service,mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {

        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(
                Result.success(
                    playlistsRaw
                )
            )
        })
        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)
        return PlaylistRepository(service,mapper)
    }
}