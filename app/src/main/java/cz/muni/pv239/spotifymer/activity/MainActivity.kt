package cz.muni.pv239.spotifymer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.activity.SwipeMenu.SwipeOverviewActivity
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI
import cz.muni.pv239.spotifymer.credentials.SPOTIFY_APP_REMOTE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val overviewIntent = Intent(this@MainActivity, SwipeOverviewActivity::class.java)
        startActivity(overviewIntent)
    }

    override fun onStart() {
        super.onStart()

        this.connectToSpotify()
    }

    private fun connected() {
        // Then we will write some more code here.
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(SPOTIFY_APP_REMOTE)
    }


    private fun connectToSpotify() {
        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                SPOTIFY_APP_REMOTE = appRemote
                // Now you can start interacting with App Remote
                Toast.makeText(this@MainActivity, "Succesfully connected!", Toast.LENGTH_SHORT)
                    .show()
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                Toast.makeText(this@MainActivity, "Error occured :(", Toast.LENGTH_SHORT).show()
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }
}
