package cz.muni.pv239.spotifymer.util

import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import cz.muni.pv239.spotifymer.SpotifyDownloadDialogFragment
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI

/*
class SpotifyRemote(activity: AppCompatActivity) {
    var spotifyAppRemote: SpotifyAppRemote? = null

    var connectionParams: ConnectionParams? = null

    val SCOPES = listOf("app-remote-control")

    var connectionListener: Connector.ConnectionListener? = null

    init {
        if (InternetConnection.isNetworkReacheable(activity.applicationContext)) {
            connectionListener = object :
                Connector.ConnectionListener {
                override fun onConnected(remote: SpotifyAppRemote) {
                    spotifyAppRemote = remote
                    println("CONNECTED")
                    // setup all the things
                }

                override fun onFailure(error: Throwable?) {
                    if (error is NotLoggedInException || error is UserNotAuthorizedException) {
                        // meh
                    } else if (error is CouldNotFindSpotifyApp) {
                        val dialog = SpotifyDownloadDialogFragment()
                        dialog.show(activity.supportFragmentManager, "download_dialog")

                    }
                }
            }

            connectionParams = ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build()
        }
    }
}
*/