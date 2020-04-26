package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.endpoints.public.TrackAttribute
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SearchAttributesListAdapter
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.databinding.FragmentRecommendMenuBinding
import cz.muni.pv239.spotifymer.model.PlaylistAttributes

class RecommendMenuFragment : Fragment() {

    private lateinit var api: SpotifyAppApi

    private var _binding: FragmentRecommendMenuBinding? = null
    private val binding get() = _binding!!

    private val model: PlaylistAttributes by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = spotifyAppApi(
            CLIENT_ID,
            CLIENT_SECRET
        ).build()

        this.intiRecyclerView(view.context)
        this.intiEnergySeekBar()

        binding.addSearchButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.content_layout, SearchMenuFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.searchRecommendationsButton.setOnClickListener {
            val artists = model.getSearches().filter { it.type == "Artist" }.map { it.id }
            val tracks = model.getSearches().filter { it.type == "Track" }.map { it.id }
            val genres = model.getSearches().filter { it.type == "Genre" }.map { it.id }
            val attributes = ArrayList<TrackAttribute<*>>()
            attributes.add(TrackAttribute(model.getEnergy().getType(), model.getEnergy().value))
            val results = api.browse.getTrackRecommendations(artists, tracks, genres, 10, Market.SK).complete()
            results.tracks.map { println(it.name) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun intiRecyclerView(context: Context) {
        binding.searchAttributesRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SearchAttributesListAdapter(model)
        binding.searchAttributesRecyclerView.adapter = adapter
    }

    private fun intiEnergySeekBar() {
        binding.energySeekbar.progress = 50
        binding.energySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val energy = i / 100.0f
                binding.energyText.text = energy.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val energy = seekBar.progress / 100.0f
                model.setEnergy(energy)
            }
        })
    }
}
