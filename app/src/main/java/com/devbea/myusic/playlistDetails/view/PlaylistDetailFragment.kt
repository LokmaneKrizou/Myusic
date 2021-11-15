package com.devbea.myusic.playlistDetails.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.devbea.myusic.databinding.FragmentPlaylistDetailBinding
import com.devbea.myusic.playlistDetails.viewmodel.PlaylistDetailViewModel
import com.devbea.myusic.playlistDetails.viewmodel.PlaylistDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    val args: PlaylistDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: PlaylistDetailViewModelFactory
    private val viewModel: PlaylistDetailViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        listenToViewModel()
        viewModel.getPlaylistDetailsWithId(args.playlistId)
        return binding.root
    }

    private fun listenToViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { playlist ->
            onScreenStateChange(playlist)
        }
    }

    private fun onScreenStateChange(screenState: PlaylistDetailViewModel.ScreenState) {
        when (screenState) {
            is PlaylistDetailViewModel.ScreenState.FailureState -> {
                Log.e(this.javaClass.name, screenState.errorMessage)
            }
            is PlaylistDetailViewModel.ScreenState.SuccessState -> {
                binding.apply {
                    playlistDetailsName.text = screenState.playlist.name
                    playlistDetailsDescription.text = screenState.playlist.details
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}