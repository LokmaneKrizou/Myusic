package com.devbea.myusic.playlistDetails.repository

import com.devbea.myusic.playlistDetails.model.PlaylistDetails
import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import com.devbea.myusic.playlistDetails.service.PlaylistDetailsService
import com.devbea.myusic.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.verification.Times
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class PlaylistDetailsRepositoryShould : BaseUnitTest() {
    private lateinit var repository: PlaylistDetailsRepository
    private val service: PlaylistDetailsService = mock()
    private val mapper: PlaylistDetailsMapper = mock()
    private val playlistDetails = mock<PlaylistDetails>()
    private val playlistDetailsRaw = mock<PlaylistDetailsRaw>()
    private val exception = RuntimeException("Something went south")
    private val mapperException = RuntimeException("Something went Wrong")

    @Test
    fun `get Play list details From Service`() = runBlockingTest {
        repository = mockSuccessfulCase()
        repository.getPlaylistWithId(PLAYLIST_ID)
        verify(service, Times(1)).fetchPlaylistWithId(PLAYLIST_ID)
    }

    @Test
    fun `emit Play list details From Service`() = runBlockingTest {
        repository = mockSuccessfulCase()
        assertEquals(playlistDetails, repository.getPlaylistWithId(PLAYLIST_ID).first().getOrNull())

    }

    @Test
    fun `delegate playlistDetails mapping to PlaylistDetailsMapper`() = runBlockingTest {
        repository = mockSuccessfulCase()
        repository.getPlaylistWithId(PLAYLIST_ID).first()
        verify(mapper, times(1)).invoke(playlistDetailsRaw)
    }

    @Test
    fun `emit error when mapper returns null`() = runBlockingTest {
        repository = mockFailureMapperCase()
        assertTrue(
            repository.getPlaylistWithId(PLAYLIST_ID).first()
                .exceptionOrNull() is java.lang.RuntimeException
        )
        assertEquals(
            mapperException.message,
            repository.getPlaylistWithId(PLAYLIST_ID).first().exceptionOrNull()?.message
        )
        repository.getPlaylistWithId(PLAYLIST_ID)
    }

    @Test
    fun `emit error from Service when receive error`() = runBlockingTest {
        repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylistWithId(PLAYLIST_ID).first().exceptionOrNull())
    }


    private suspend fun mockSuccessfulCase(): PlaylistDetailsRepository {
        whenever(service.fetchPlaylistWithId(any())).thenReturn(flow {
            emit(Result.success(playlistDetailsRaw))
        })
        whenever(mapper.invoke(playlistDetailsRaw)).thenReturn(playlistDetails)
        return PlaylistDetailsRepository(service, mapper)
    }

    private suspend fun mockFailureMapperCase(): PlaylistDetailsRepository {
        whenever(service.fetchPlaylistWithId(any())).thenReturn(flow {
            emit(Result.success(playlistDetailsRaw))
        })
        whenever(mapper.invoke(playlistDetailsRaw)).thenReturn(null)
        return PlaylistDetailsRepository(service, mapper)
    }

    private suspend fun mockFailureCase(): PlaylistDetailsRepository {
        whenever(service.fetchPlaylistWithId(any())).thenReturn(flow {
            emit(
                Result.failure<PlaylistDetailsRaw>(
                    exception
                )
            )
        })

        return PlaylistDetailsRepository(service, mapper)
    }

    companion object {
        private const val PLAYLIST_ID = "1"
    }
}