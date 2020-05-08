package cz.muni.pv239.spotifymer.view.search_menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamratzman.spotify.SpotifyAppApi
import cz.muni.pv239.spotifymer.R

import cz.muni.pv239.spotifymer.adapter.SearchAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentSearchBinding
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.util.SearchType
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import cz.muni.pv239.spotifymer.view_model.SearchViewModel

class SearchMenuFragment : Fragment() {

    lateinit var api: SpotifyAppApi

    private var searchViewModel: SearchViewModel? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: SearchAdapter

    private var activeSearchType = SearchType.ARTIST

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = SpotifyWebApi.getInstance()

        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        renderRecyclerView(listOf())

        binding.searchButton.setOnClickListener {
            val search = binding.searchBar.text.toString()
            searchViewModel?.search(search, activeSearchType)
                ?.observe(viewLifecycleOwner, Observer { renderRecyclerView(it) })
        }
        prepareButton(view.context, SearchType.ARTIST)

        binding.searchArtistsButton.setOnClickListener {
            prepareButton(view.context, SearchType.ARTIST)
        }
        binding.searchTracksButton.setOnClickListener {
            prepareButton(view.context, SearchType.TRACK)
        }
        binding.searchGenresButton.setOnClickListener {
            prepareButton(view.context, SearchType.GENRE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareButton(context: Context, type: SearchType) {
        resetButtonsColor(context)
        val color = ContextCompat.getColor(context, R.color.colorPrimaryVariant)
        when (activeSearchType) {
            SearchType.ARTIST -> binding.searchArtistsButton.setBackgroundColor(color)
            SearchType.TRACK -> binding.searchTracksButton.setBackgroundColor(color)
            SearchType.GENRE -> binding.searchGenresButton.setBackgroundColor(color)
        }
        activeSearchType = type
    }

    private fun resetButtonsColor(context: Context) {
        val color = ContextCompat.getColor(context, R.color.colorPrimary)
        binding.searchArtistsButton.setBackgroundColor(color)
        binding.searchTracksButton.setBackgroundColor(color)
        binding.searchGenresButton.setBackgroundColor(color)
    }

    private fun renderRecyclerView(searchList: List<Search>?) {
        adapter = SearchAdapter(searchList, searchViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.searchRecyclerView.layoutManager = layoutManager
        binding.searchRecyclerView.adapter = adapter
    }
}
