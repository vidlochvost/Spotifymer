package cz.muni.pv239.spotifymer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.repository.PlaylistRepository
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import kotlinx.coroutines.launch
import java.util.*


class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: PlaylistRepository = PlaylistRepository(application)

    private val spotifyApi = SpotifyWebApi.getInstance()

    fun getPlaylists(): LiveData<List<Playlist>>? {
        return repository.getPlaylists()
    }

    fun setPlaylist(playlist: Playlist): LiveData<Long> {
        val playlistId = MutableLiveData<Long>()
        viewModelScope.launch {
            playlistId.value = repository.setPlaylist(playlist)
        }
        return playlistId
    }

    fun newPlaylist(playlistName: String): LiveData<Long>? {
        return setPlaylist(
            Playlist(
                playlistName,
                "https://picsum.photos/id/${(0..1000).random()}/80/80"
            )
        )
    }

    fun removePlaylist(id: Long) = viewModelScope.launch {
        repository.removePlaylist(id)
    }
}