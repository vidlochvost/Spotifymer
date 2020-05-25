package cz.muni.pv239.spotifymer.view.swipe_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.PlaylistListAdapter
import cz.muni.pv239.spotifymer.databinding.PlaylistsLayoutBinding
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.view.search_menu.NewPlaylistActivity
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel


class PlaylistsFragment : Fragment() {

    private var _binding: PlaylistsLayoutBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null

    lateinit var adapter: PlaylistListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initItemTouchHelper()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        trackViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)

        this.playlistViewModel
            ?.getPlaylists()
            ?.observe(viewLifecycleOwner, Observer { renderRecyclerView(it) })

        binding.recommendButton.setOnClickListener {
            val intent = Intent(context, NewPlaylistActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initItemTouchHelper() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                adapter.removePlaylist(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(binding.playlistRecyclerView)
    }

    private fun renderRecyclerView(playlists: List<Playlist>?) {
        adapter = PlaylistListAdapter(playlists, playlistViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.playlistRecyclerView.layoutManager = layoutManager
        binding.playlistRecyclerView.adapter = adapter
    }
}
