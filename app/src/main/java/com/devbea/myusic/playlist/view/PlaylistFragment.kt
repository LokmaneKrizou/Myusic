package com.devbea.myusic.playlist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devbea.myusic.databinding.FragmentPlaylistBinding
import com.devbea.myusic.playlist.model.Playlist
import com.devbea.myusic.playlist.viewmodel.PlaylistViewModel
import com.devbea.myusic.playlist.viewmodel.PlaylistViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        setupViewModel()
        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            binding.loader.isVisible = loading
        }

        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            playlists.getOrNull()?.let { setupList(binding.playlistsList, it) }
                ?: run { Log.e("Error", "error") }

        }
        return binding.root
    }

    private fun setupList(
        view: RecyclerView,
        playlists: List<Playlist>
    ) {
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                //TODO: Navigate to Playlist details
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}