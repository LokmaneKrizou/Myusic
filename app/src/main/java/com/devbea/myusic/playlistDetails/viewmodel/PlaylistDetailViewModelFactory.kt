package com.devbea.myusic.playlistDetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devbea.myusic.playlistDetails.repository.PlaylistDetailsRepository
import javax.inject.Inject

class PlaylistDetailViewModelFactory @Inject constructor(private val repository: PlaylistDetailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistDetailViewModel(repository) as T
    }

}