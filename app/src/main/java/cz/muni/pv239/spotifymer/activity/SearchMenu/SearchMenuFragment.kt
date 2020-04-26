package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import cz.muni.pv239.spotifymer.R

import cz.muni.pv239.spotifymer.adapter.SearchAdapter
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.databinding.FragmentSearchBinding
import cz.muni.pv239.spotifymer.model.PlaylistAttributes
import cz.muni.pv239.spotifymer.model.Search

class SearchMenuFragment : Fragment() {

    lateinit var api: SpotifyAppApi

    private val model: PlaylistAttributes by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var activeSearchType = "Artist"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = spotifyAppApi(
            CLIENT_ID,
            CLIENT_SECRET
        ).build()


        binding.searchRecyclerView.layoutManager = LinearLayoutManager(view.context)
        val adapter = SearchAdapter(ArrayList(), model)
        binding.searchRecyclerView.adapter = adapter

        binding.searchButton.setOnClickListener {
            println(activeSearchType)
            val search = binding.searchBar.text.toString()
            val results = when (activeSearchType) {
                "Artist" ->
                    api.search.searchArtist(search).complete()
                        .map {
                            Search(
                                it.images.getOrNull(2)?.url,
                                it.name,
                                it.id,
                                activeSearchType
                            )
                        }
                "Track" -> api.search.searchTrack(search).complete()
                    .map {
                        Search(
                            it.album.images.getOrNull(2)?.url,
                            it.name,
                            it.id,
                            activeSearchType
                        )
                    }
                "Genre" -> api.browse.getAvailableGenreSeeds().complete()
                    .filter { value -> value.contains(search) }
                    .map { Search(null, it, it, activeSearchType) }
                else -> ArrayList()
            }
            adapter.changeData(results)
        }

        binding.searchArtistsButton.setOnClickListener {
            resetButtonsColor(view.context)
            val color = ContextCompat.getColor(view.context, R.color.colorPrimaryVariant)
            binding.searchArtistsButton.setBackgroundColor(color)
            activeSearchType = "Artist"
        }
        binding.searchTracksButton.setOnClickListener {
            resetButtonsColor(view.context)
            val color = ContextCompat.getColor(view.context, R.color.colorPrimaryVariant)
            binding.searchTracksButton.setBackgroundColor(color)
            activeSearchType = "Track"
        }
        binding.searchGenresButton.setOnClickListener {
            resetButtonsColor(view.context)
            val color = ContextCompat.getColor(view.context, R.color.colorPrimaryVariant)
            binding.searchGenresButton.setBackgroundColor(color)
            activeSearchType = "Genre"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resetButtonsColor(context: Context) {
        val color = ContextCompat.getColor(context, R.color.colorPrimary)
        binding.searchArtistsButton.setBackgroundColor(color)
        binding.searchTracksButton.setBackgroundColor(color)
        binding.searchGenresButton.setBackgroundColor(color)
    }
}
