package cz.muni.pv239.spotifymer.view.songs_overview

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso

import cz.muni.pv239.spotifymer.adapter.SongListAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentSongsManagementBinding
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.util.RandomCover
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel

class SongsManagementFragment(
    private val playlistId: Long
) : Fragment() {

    private var _binding: FragmentSongsManagementBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null

    lateinit var adapter: SongListAdapter

    private val spotifyApi = SpotifyWebApi.getInstance()

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


        binding.playlistName.setOnClickListener {
            showKeyboard()
            binding.playlistName.requestFocus()
        }


        binding.playlistName.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.playlistName.clearFocus()
                hideKeyboard()
                this.playlistViewModel?.getPlaylist(playlistId)?.observe(
                    viewLifecycleOwner,
                    Observer { playlist ->
                        playlist.name = binding.playlistName.text.toString()
                        this.playlistViewModel?.updatePlaylist(playlist)
                    })
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        this.trackViewModel
            ?.getTracks(playlistId)
            ?.observe(viewLifecycleOwner, Observer { songs ->
                renderRecyclerView(songs)
                binding.image.setOnClickListener {
                    // TODO shuffle playlist
                }
            })

        playlistViewModel?.getPlaylist(playlistId)?.observe(
            viewLifecycleOwner,
            Observer { playlist ->
                Picasso.get().load(playlist.imageUrl).into(binding.image)
                binding.playlistName.setText(playlist.name)
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

    private fun showKeyboard() {
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager     // Muze spadnout, kdyz bude activity null
        im.showSoftInput(binding.playlistName, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
