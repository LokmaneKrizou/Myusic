package com.devbea.myusic.playlist.repository

import com.devbea.myusic.R
import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.model.PlaylistRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(playlistsRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistsRaw.map {
            val image = when (it.category.lowercase()) {
                ROCK -> R.mipmap.rock
                else -> R.mipmap.playlist
            }
            Playlist(it.id, it.category, it.name, image)
        }
    }

    companion object {
        private const val ROCK = "rock"
    }
}
