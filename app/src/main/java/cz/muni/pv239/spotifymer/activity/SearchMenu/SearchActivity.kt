package cz.muni.pv239.spotifymer.activity.SearchMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET

class SearchActivity : AppCompatActivity() {

    lateinit var api: SpotifyAppApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        api = spotifyAppApi(
            CLIENT_ID,
            CLIENT_SECRET
        ).build()

        val searchButton = findViewById<FloatingActionButton>(R.id.search_button)
        val searchBar = findViewById<TextInputEditText>(R.id.search_bar)

        searchButton.setOnClickListener {
            val search = searchBar.text.toString()
            val searchResult = api.search.searchArtist(search).complete()
            for (i in 0 until searchResult.size) {
                println("""Name: ${searchResult[i].name}""")
            }
        }
    }
}
