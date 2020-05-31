package cz.muni.pv239.spotifymer.util

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.spotifyAppApi
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET
import cz.muni.pv239.spotifymer.credentials.REDIRECT_URI

object SpotifyWebApi {

    private val INSTANCE: SpotifyAppApi =
        spotifyAppApi(CLIENT_ID, CLIENT_SECRET).build()

    fun getInstance(): SpotifyAppApi {
        return INSTANCE
    }
}