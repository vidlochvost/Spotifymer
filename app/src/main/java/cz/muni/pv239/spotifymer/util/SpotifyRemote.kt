package cz.muni.pv239.spotifymer.util

import android.app.Activity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI


class SpotifyRemote(activity: Activity) {
    val REQUEST_CODE = 1337

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

                    val builder = AuthenticationRequest.Builder(
                        CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI
                    )

                    builder.setScopes(arrayOf("streaming"))
                    val request = builder.build()

                    AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request)
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