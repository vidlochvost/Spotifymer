package cz.muni.pv239.spotifymer.activity.SwipeMenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.activity.SearchMenu.SearchActivity
import cz.muni.pv239.spotifymer.adapter.RecommendationsListAdapter
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.model.RecommendedPlaylist

class RecommendationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recommendations_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.playlist_recycler_view)
        val recommendButton = view.findViewById<Button>(R.id.recommend_button)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val playlists = this.createDummyList()
        val adapter = RecommendationsListAdapter(playlists, view.context)
        recyclerView.adapter = adapter

        recommendButton.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createDummyList(): ArrayList<RecommendedPlaylist> {
        val resultList = ArrayList<RecommendedPlaylist>()
        resultList.add(
            RecommendedPlaylist(
                "https://i.scdn.co/image/54b3222c8aaa77890d1ac37b3aaaa1fc9ba630ae",
                "Playlist1"
            )
        )
        resultList.add(
            RecommendedPlaylist(
                "https://i.scdn.co/image/5a73a056d0af707b4119a883d87285feda543fbb",
                "Playlist2"
            )
        )
        return resultList
    }
}
