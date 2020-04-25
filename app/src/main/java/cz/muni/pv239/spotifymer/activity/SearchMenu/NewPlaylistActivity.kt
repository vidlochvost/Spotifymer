package cz.muni.pv239.spotifymer.activity.SearchMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.PlaylistAttributes

class NewPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState != null) {
            return;
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_layout, RecommendMenuFragment())
            .commit()
    }


}
