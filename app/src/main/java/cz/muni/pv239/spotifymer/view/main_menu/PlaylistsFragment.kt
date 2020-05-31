package cz.muni.pv239.spotifymer.view.main_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.muni.pv239.spotifymer.adapter.PlaylistCardHolder
import cz.muni.pv239.spotifymer.adapter.PlaylistListAdapter
import cz.muni.pv239.spotifymer.databinding.PlaylistsLayoutBinding
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.util.InternetConnection
import cz.muni.pv239.spotifymer.util.SimpleItemTouchCallback
import cz.muni.pv239.spotifymer.view.search_menu.NewPlaylistActivity
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel


class PlaylistsFragment : Fragment() {

    private var _binding: PlaylistsLayoutBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null

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

        // requireActivity() zpusobi, ze pokud aktivita v ten moment uz neni, crashne vam appka. A to se muze snadno stat.
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        trackViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)

        val adapter = PlaylistListAdapter(arrayListOf(), playlistViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.playlistRecyclerView.layoutManager = layoutManager      // LinearLayoutManager je mozne pridat i primo v XML
        binding.playlistRecyclerView.adapter = adapter
        ItemTouchHelper(SimpleItemTouchCallback(adapter)).attachToRecyclerView(binding.playlistRecyclerView)

        this.playlistViewModel
            ?.getPlaylists()
            ?.observe(viewLifecycleOwner, Observer { adapter.renderDataset(ArrayList(it) ) })

        binding.recommendButton.setOnClickListener {
            if (InternetConnection.isNetworkReacheable(context)) {
                val intent = Intent(context, NewPlaylistActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Missing Internet connection!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
