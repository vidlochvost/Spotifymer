package cz.muni.pv239.spotifymer.util

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.SpotifyDownloadDialogFragment
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI


class SpotifyRemote(activity: AppCompatActivity) {
    val REQUEST_CODE = 1337

    val SCOPES = arrayOf("streaming", "app-remote-control", "user-modify-playback-state")

    var spotifyAppRemote: SpotifyAppRemote? = null

    var connectionParams: ConnectionParams? = null

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
                        val builder = AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)

                        builder.setScopes(SCOPES)
                        builder.setShowDialog(true)
                        val request = builder.build()

                        AuthenticationClient.openLoginInBrowser(activity, request)
                    } else if (error is CouldNotFindSpotifyApp) {
                        val dialog = SpotifyDownloadDialogFragment()
                        dialog.isCancelable = false
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