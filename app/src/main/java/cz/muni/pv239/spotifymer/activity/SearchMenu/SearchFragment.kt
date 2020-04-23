package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SearchResultsAdapter
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.model.SearchResult

class SearchFragment : Fragment() {

    lateinit var api: SpotifyAppApi

    private lateinit var searchBar: TextInputEditText
    private lateinit var searchButton: MaterialButton
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = spotifyAppApi(
            CLIENT_ID,
            CLIENT_SECRET
        ).build()

        val searchResults: MutableList<SearchResult> = ArrayList()
        searchButton = view.findViewById(R.id.search_button)
        searchBar = view.findViewById(R.id.search_bar)

        recyclerView = view.findViewById(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val adapter = SearchResultsAdapter(searchResults, view.context)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val search = searchBar.text.toString()
            val searchResult = api.search.searchArtist(search).complete()
            val temp: MutableList<SearchResult> = ArrayList(10)
            for (i in 0..9) {
                temp.add(SearchResult(searchResult[i].images[2].url, searchResult[i].name))
            }
            //adapter.changeData(temp)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}
