package cz.muni.pv239.spotifymer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.endpoints.public.TrackAttribute
import com.adamratzman.spotify.models.Track
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.repository.TrackRepository
import cz.muni.pv239.spotifymer.util.SearchType
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = TrackRepository(application)

    private val spotifyApi = SpotifyWebApi.getInstance()

    fun getTracks() = repository.getTracks()

    fun getTracks(playlistId: Long) = repository.getTracks(playlistId)

    fun getTrack(trackId: Long) = repository.getTrack(trackId)

    fun setTrack(song: Song) {
        viewModelScope.launch {
            repository.setTrack(song)
        }
    }

    fun setTracks(songs: List<Song>) {
        viewModelScope.launch {
            repository.setTracks(songs)
        }
    }

    fun bindTracksToPlaylist(playlistId: Long?, songs: List<Track>) {
        if (playlistId == null) {
            return
        }

        GlobalScope.launch {
            withContext(IO) {
                val trackList = songs.map { track ->
                    Song(
                        playlistId,
                        track.album.images[0].url,
                        track.name,
                        track.artists[0].name,
                        track.uri.uri
                    )
                }
                repository.setTracks(trackList)
            }
        }
    }
}