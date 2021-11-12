package com.devbea.myusic.playlistDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devbea.myusic.playlistDetails.model.PlaylistDetails
import com.devbea.myusic.playlistDetails.repository.PlaylistDetailsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistDetailViewModel @Inject constructor(private val repository: PlaylistDetailsRepository) :
    ViewModel() {
    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState


    fun getPlaylistDetailsWithId(id: String) {
        viewModelScope.launch {
            repository.getPlaylistWithId(id).firstOrNull()?.let {result->
                when (result.isSuccess) {
                    true -> {
                        result.getOrNull()?.let {
                            _screenState.postValue(ScreenState.SuccessState(it))
                        } ?: run {
                            _screenState.postValue(ScreenState.FailureState("unknown error"))
                        }
                    }
                    else -> {
                        _screenState.postValue(
                            ScreenState.FailureState(
                                result.exceptionOrNull()?.message ?: "unknown error"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class ScreenState {
        class SuccessState(val playlist: PlaylistDetails) : ScreenState()
        class FailureState(val errorMessage: String) : ScreenState()
    }

}