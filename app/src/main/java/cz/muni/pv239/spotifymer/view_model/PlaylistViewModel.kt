package cz.muni.pv239.spotifymer.view_model

import android.app.Application
import androidx.lifecycle.*
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.repository.PlaylistRepository
import kotlinx.coroutines.launch


class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private var repository= PlaylistRepository(application)

    fun getPlaylists(): LiveData<List<Playlist>>? {
        return repository.getPlaylists()
    }

    fun setPlaylist(playlist: Playlist): LiveData<Long> {
        val playlistId = MutableLiveData<Long>()
        viewModelScope.launch {
            playlistId.postValue(repository.setPlaylist(playlist))
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