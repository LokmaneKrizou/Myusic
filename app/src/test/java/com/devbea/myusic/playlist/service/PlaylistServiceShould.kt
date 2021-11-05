package com.devbea.myusic.playlist.service

import com.devbea.myusic.playlist.api.PlaylistAPI
import com.devbea.myusic.playlist.model.PlaylistRaw
import com.devbea.myusic.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something when south")

    @Test
    fun `fetch all playlists from Api`() = runBlockingTest {
        service = mockSuccessfulCase()
        service.fetchPlaylists().firstOrNull()
        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun `emits all playlists from Api`() = runBlockingTest {
        service = mockSuccessfulCase()
        service.fetchPlaylists()
        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun `emits error from Api`() = runBlockingTest {
        service = mockFailureCase()
        service.fetchPlaylists()
        assertEquals(
            exception,
            service.fetchPlaylists().firstOrNull()?.exceptionOrNull() as RuntimeException
        )
    }

    private suspend fun mockFailureCase(): PlaylistService {
        whenever(api.fetchAllPlaylists()).thenThrow(exception)
        return PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase(): PlaylistService {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)
        return PlaylistService(api)
    }
}