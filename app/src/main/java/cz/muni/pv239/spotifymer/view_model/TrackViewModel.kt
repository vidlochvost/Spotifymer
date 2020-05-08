package cz.muni.pv239.spotifymer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.endpoints.public.TrackAttribute
import cz.muni.pv239.spotifymer.model.PlaylistAttributes
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.repository.TrackRepository
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TrackRepository = TrackRepository(application)

    private val spotifyApi = SpotifyWebApi.getInstance()

    fun getTracks() = repository.getTracks()

    fun getTracks(playlistId: Long) = repository.getTracks(playlistId)

    fun setTrack(song: Song) {
        viewModelScope.launch {
            repository.setTrack(song)
        }
    }

    fun setTracks(songs: List<Song>) {
        viewModelScope.launch {
            for (track in songs) {
                repository.setTrack(track)
            }
        }
    }

    fun bindTracksToPlaylist(playlistId: Long?, model: PlaylistAttributes) {
        if (playlistId == null) {
            return
        }

        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                val artists = model.getSearches().filter { it.type == "Artist" }.map { it.id }
                val tracks = model.getSearches().filter { it.type == "Track" }.map { it.id }
                val genres = model.getSearches().filter { it.type == "Genre" }.map { it.id }
                val attributes = ArrayList<TrackAttribute<*>>()
                attributes.add(TrackAttribute(model.getEnergy().getType(), model.getEnergy().value))

                val recommendationResponse = spotifyApi.browse.getTrackRecommendations(
                    seedArtists = artists,
                    seedTracks = tracks,
                    seedGenres = genres,
                    targetAttributes = attributes
                ).complete()

                val trackList = recommendationResponse.tracks.map { track ->
                    println("Track: ${track.name}")
                    Song(
                        playlistId,
                        track.album.images[0].url,
                        track.name,
                        track.href
                    )
                }
                repository.setTracks(trackList)
            }
        }
    }
}