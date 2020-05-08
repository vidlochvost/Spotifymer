package cz.muni.pv239.spotifymer.util

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import cz.muni.pv239.spotifymer.credentials.CLIENT_ID
import cz.muni.pv239.spotifymer.credentials.CLIENT_SECRET

object SpotifyWebApi {
    private var INSTANCE: SpotifyAppApi = spotifyAppApi(
        CLIENT_ID,
        CLIENT_SECRET
    ).build()

    fun getInstance(): SpotifyAppApi {
        return INSTANCE
    }
}