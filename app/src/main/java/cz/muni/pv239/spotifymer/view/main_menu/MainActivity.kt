package cz.muni.pv239.spotifymer.view.main_menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.muni.pv239.spotifymer.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.menu_layout, PlaylistsFragment())
            .commit()
    }

}
