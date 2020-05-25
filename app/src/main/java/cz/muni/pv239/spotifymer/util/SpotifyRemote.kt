package cz.muni.pv239.spotifymer.util

import android.widget.Toast
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI

object SpotifyRemote {

    var spotifyAppRemote: SpotifyAppRemote? = null

    var connectionParams: ConnectionParams

    var connectionListener: Connector.ConnectionListener

    init {
        connectionListener =  object :
            Connector.ConnectionListener {
            override fun onConnected(remote: SpotifyAppRemote) {
                spotifyAppRemote = remote
                println("CONNECTED")
                // setup all the things
            }

            override fun onFailure(error: Throwable?) {
                if (error is NotLoggedInException || error is UserNotAuthorizedException) {
                    // Show login button and trigger the login flow from auth library when clicked
                } else if (error is CouldNotFindSpotifyApp) {
                    // Show button to download Spotify
                }
            }
        }
        connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()
    }
}