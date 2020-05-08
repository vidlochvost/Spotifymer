package cz.muni.pv239.spotifymer.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cz.muni.pv239.spotifymer.database.AppDatabase
import cz.muni.pv239.spotifymer.database.TrackDao
import cz.muni.pv239.spotifymer.model.Song
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class TrackRepository(application: Application) {

    private var trackDao: TrackDao?

    init {
        val db = AppDatabase.getInstance(application)
        trackDao = db.trackDao()
    }

    fun getTracks() = trackDao?.getAll()

    fun getTracks(playlistId: Long) = trackDao?.getAll(playlistId)

    suspend fun setTrack(song: Song) = withContext(IO) {
        trackDao?.insert(song)
    }

    suspend fun setTracks(songs: List<Song>) = withContext(IO) {
        for (track in songs) {
            trackDao?.insert(track)
        }
    }
}