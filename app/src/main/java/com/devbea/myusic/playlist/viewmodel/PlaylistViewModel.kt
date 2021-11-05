package com.devbea.myusic.playlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.devbea.myusic.playlist.repository.PlaylistRepository
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class PlaylistViewModel @Inject constructor(private val repository: PlaylistRepository) :
    ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlists = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onCompletion { loader.postValue(false) }
            .asLiveData())
    }
}
