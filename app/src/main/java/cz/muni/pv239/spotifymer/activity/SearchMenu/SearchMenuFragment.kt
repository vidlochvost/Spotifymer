package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SearchAdapter
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.model.PlaylistAttributes
import cz.muni.pv239.spotifymer.model.Search

class SearchMenuFragment : Fragment() {

    lateinit var api: SpotifyAppApi

    private lateinit var searchBar: TextInputEditText
    private lateinit var searchButton: MaterialButton
    private lateinit var recyclerView: RecyclerView

    private val model: PlaylistAttributes by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = spotifyAppApi(
            CLIENT_ID,
            CLIENT_SECRET
        ).build()

        val searchResults: MutableList<Search> = ArrayList()
        searchButton = view.findViewById(R.id.search_button)
        searchBar = view.findViewById(R.id.search_bar)

        recyclerView = view.findViewById(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val adapter = SearchAdapter(searchResults, model)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val search = searchBar.text.toString()
            val searchResult = api.search.searchArtist(search).complete()
                .map { Search(it.images.getOrNull(2)?.url, it.name, it.uri.uri, "Artist") }
            adapter.changeData(searchResult)
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
