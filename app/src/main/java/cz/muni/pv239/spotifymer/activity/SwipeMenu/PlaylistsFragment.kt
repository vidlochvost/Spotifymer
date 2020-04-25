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
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.activity.SearchMenu.NewPlaylistActivity
import cz.muni.pv239.spotifymer.adapter.PlaylistListAdapter
import cz.muni.pv239.spotifymer.model.Playlist

class PlaylistsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlists_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.playlist_recycler_view)
        val recommendButton = view.findViewById<Button>(R.id.recommend_button)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val playlists = this.createDummyList()
        val adapter = PlaylistListAdapter(playlists)
        recyclerView.adapter = adapter

        recommendButton.setOnClickListener {
            val intent = Intent(context, NewPlaylistActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createDummyList(): ArrayList<Playlist> {
        val resultList = ArrayList<Playlist>()
        resultList.add(
            Playlist(
                "https://i.scdn.co/image/54b3222c8aaa77890d1ac37b3aaaa1fc9ba630ae",
                "Playlist1"
            )
        )
        resultList.add(
            Playlist(
                "https://i.scdn.co/image/5a73a056d0af707b4119a883d87285feda543fbb",
                "Playlist2"
            )
        )
        return resultList
    }
}
