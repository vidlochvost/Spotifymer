package cz.muni.pv239.spotifymer.view.songs_overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.databinding.FragmentSongOverviewBinding
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.util.SpotifyWebApi

class SongOverviewFragment(private val song: Song) :
    Fragment() {

    private var _binding: FragmentSongOverviewBinding? = null
    private val binding get() = _binding!!

    private val spotifyApi = SpotifyWebApi.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.author.text = song.author
        binding.title.text = song.name
        Picasso.get().load(song.imageUrl).into(binding.image)
        binding.playButton.setOnClickListener {
            //spotifyApi.player.startPlayback(tracksToPlay = listOf(song.spotifyUrl))
            /*
            spotifyRemote.spotifyAppRemote?.let {
                    if (it.isConnected) {
                        it.playerApi?.play(song.spotifyUrl)
                    }
                }
                */
        }
    }
}
