package cz.muni.pv239.spotifymer.view.songs_overview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.muni.pv239.spotifymer.R


class SongsOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_overview)

        val playlistId = intent.getLongExtra("PLAYLIST_ID", 0)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.songs_content, SongsManagementFragment(playlistId))
            .commit()
    }
}
