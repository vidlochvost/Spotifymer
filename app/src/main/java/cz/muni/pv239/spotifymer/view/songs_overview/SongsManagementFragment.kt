package cz.muni.pv239.spotifymer.view.songs_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import cz.muni.pv239.spotifymer.adapter.SongListAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentSongsManagementBinding
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel

class SongsManagementFragment(private val playlistId: Long) : Fragment() {

    private var _binding: FragmentSongsManagementBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null

    lateinit var adapter: SongListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongsManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.renderRecyclerView(listOf())

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        trackViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)

        this.trackViewModel
            ?.getTracks(playlistId)
            ?.observe(viewLifecycleOwner, Observer { songs -> renderRecyclerView(songs) })


    }

    private fun renderRecyclerView(songs: List<Song>?) {
        adapter = SongListAdapter(parentFragmentManager, songs)
        val layoutManager = LinearLayoutManager(this.context)
        binding.songsRecyclerView.layoutManager = layoutManager
        binding.songsRecyclerView.adapter = adapter
    }

}
