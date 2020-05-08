package cz.muni.pv239.spotifymer.repository

import android.app.Application
import androidx.lifecycle.LiveData
import cz.muni.pv239.spotifymer.database.AppDatabase
import cz.muni.pv239.spotifymer.database.PlaylistDao
import cz.muni.pv239.spotifymer.model.Playlist
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PlaylistRepository(application: Application) {

    private var playlistDao: PlaylistDao?

    init {
        val db = AppDatabase.getInstance(application)
        playlistDao = db.playlistDao()
    }

    fun getPlaylists() = playlistDao?.getAll()

    suspend fun setPlaylist(playlist: Playlist) = withContext(IO) {
        playlistDao?.insert(playlist)
    }

    suspend fun removePlaylist(id: Long) = withContext(IO) {
        val playlist = playlistDao?.get(id)
        if (playlist != null) {
            playlistDao?.delete(playlist)
        }
    }

}