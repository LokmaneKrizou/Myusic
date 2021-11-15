package com.devbea.myusic.playlistDetails.service

import com.devbea.myusic.common.api.PlaylistAPI
import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*

class PlaylistDetailsServiceShould {
    private lateinit var service: PlaylistDetailsService
    private val api: PlaylistAPI = mock()
    private val playlistRaw:PlaylistDetailsRaw= mock()
    private val exception= RuntimeException("something went wrong")

    @Test
    fun `fetch Playlist With Id`()= runBlockingTest {
        service= PlaylistDetailsService(api)
        service.fetchPlaylistWithId("1").firstOrNull()
        verify(api, times(1)).fetchPlaylistById("1")
    }
    @Test
    fun `emit Playlist With Id`()= runBlockingTest {
        whenever(api.fetchPlaylistById(any())).thenReturn(playlistRaw)
        service= PlaylistDetailsService(api)
     val result=   service.fetchPlaylistWithId("1").firstOrNull()
       assertEquals(Result.success(playlistRaw),result)
    }

    @Test
    fun `emit error when api fails`()= runBlockingTest {
        whenever(api.fetchPlaylistById(any())).thenThrow(exception)
        service= PlaylistDetailsService(api)
        val result=   service.fetchPlaylistWithId("1").firstOrNull()
        assertEquals(Result.failure<PlaylistDetailsRaw>(exception),result)
    }
}