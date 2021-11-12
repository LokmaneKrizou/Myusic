package com.devbea.myusic.playlistDetails.repository

import com.devbea.myusic.playlistDetails.model.PlaylistDetailsRaw
import com.devbea.myusic.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistDetailsMapperShould : BaseUnitTest() {
    private val playlistRaw = PlaylistDetailsRaw("2", "Title", "Description")
    private val mapper = PlaylistDetailsMapper()
    private val playlist = mapper(playlistRaw)

    @Test
    fun `keep Same Id`() {
        assertEquals(playlistRaw.id, playlist?.id)
    }

    @Test
    fun `keep Same Name`() {
        assertEquals(playlistRaw.name, playlist?.name)

    }

    @Test
    fun `keep Same Description`() {
        assertEquals(playlistRaw.description, playlist?.description)

    }
}