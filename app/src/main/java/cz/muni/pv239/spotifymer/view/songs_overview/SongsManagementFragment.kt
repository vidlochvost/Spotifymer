package cz.muni.pv239.spotifymer.view.songs_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso

import cz.muni.pv239.spotifymer.adapter.SongListAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentSongsManagementBinding
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.util.RandomCover
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
            ?.observe(viewLifecycleOwner, Observer { songs ->
                renderRecyclerView(songs)
                binding.image.setOnClickListener {
                    (activity as SongsOverviewActivity).spotifyRemote
                        .spotifyAppRemote?.let {
                            if (it.isConnected) {
                                it.playerApi?.play(songs[0].spotifyUrl)
                                for (i in 1 until songs.size) {
                                    it.playerApi.queue(songs[i].spotifyUrl)
                                }
                            }
                        }
                }
            })

        playlistViewModel?.getPlaylist(playlistId)?.observe(
            viewLifecycleOwner,
            Observer { playlist ->
                Picasso.get().load(playlist.imageUrl).into(binding.image)
                binding.SongsTextView.text = playlist.name
                binding.image.setOnLongClickListener {
                    val image = RandomCover.generate()
                    Picasso.get().load(image).into(binding.image)
                    playlist.imageUrl = image
                    playlistViewModel?.updatePlaylist(playlist)
                    true
                }
            })
    }

    private fun renderRecyclerView(songs: List<Song>?) {
        adapter = SongListAdapter(parentFragmentManager, songs)
        val layoutManager = LinearLayoutManager(this.context)
        binding.songsRecyclerView.layoutManager = layoutManager
        binding.songsRecyclerView.adapter = adapter
    }

}
