package cz.muni.pv239.spotifymer.view.search_menu

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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
            if (search.isBlank()) {
                Toast.makeText(context, "Search field is blank!", Toast.LENGTH_SHORT).show()
            } else {
                searchViewModel?.search(search, activeSearchType)
                    ?.observe(viewLifecycleOwner, Observer { renderRecyclerView(it) })
                hideKeyboard()
            }
        }

        binding.searchArtistsChip.setOnClickListener {
            binding.searchBar.text?.clear()
            activeSearchType = SearchType.ARTIST
        }
        binding.searchTracksChip.setOnClickListener {
            binding.searchBar.text?.clear()
            activeSearchType = SearchType.TRACK
        }
        binding.searchGenresChip.setOnClickListener {
            binding.searchBar.text?.clear()
            activeSearchType = SearchType.GENRE
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

    private fun renderRecyclerView(searchList: List<Search>?) {
        adapter = SearchAdapter(searchList, searchViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.searchRecyclerView.layoutManager = layoutManager
        binding.searchRecyclerView.adapter = adapter
    }

    private fun hideKeyboard() {
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
