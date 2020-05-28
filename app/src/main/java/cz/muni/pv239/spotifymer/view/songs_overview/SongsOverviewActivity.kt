package cz.muni.pv239.spotifymer.view.songs_overview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.SpotifyAppRemote
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.util.InternetConnection
import cz.muni.pv239.spotifymer.util.SpotifyRemote


class SongsOverviewActivity : AppCompatActivity() {

    lateinit var spotifyRemote: SpotifyRemote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_overview)

        spotifyRemote = SpotifyRemote(this)

        val playlistId = intent.getLongExtra("PLAYLIST_ID", 0)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.songs_content, SongsManagementFragment(playlistId))
            .commit()
    }

    override fun onStart() {
        super.onStart()
        SpotifyAppRemote.disconnect(spotifyRemote.spotifyAppRemote)
        SpotifyAppRemote.connect(
            this,
            spotifyRemote.connectionParams,
            spotifyRemote.connectionListener
        )
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(spotifyRemote.spotifyAppRemote)
    }
}
