package com.devbea.myusic.playlist.repository

import com.devbea.myusic.R
import com.devbea.myusic.playlist.model.PlaylistRaw
import com.devbea.myusic.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {
    private val rockPlaylistRaw = PlaylistRaw("1", "Rock", "Caffe")
    private val playlistRaw = PlaylistRaw("2", "House", "House Caffe")
    private val mapper = PlaylistMapper()
    private val rockPlaylist = mapper(listOf(rockPlaylistRaw)).first()
    private val playlist = mapper(listOf(playlistRaw)).first()

    @Test
    fun `keep Same Id`() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun `keep Same Name`() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun `keep Same Category`() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun `assigns Rock Image To Rock Category`() {
        assertEquals(R.mipmap.rock, rockPlaylist.image)
    }

    @Test
    fun `assigns default Image for other Categories`() {
        print(playlist.category)
        assertEquals(R.mipmap.playlist, playlist.image)
    }
}