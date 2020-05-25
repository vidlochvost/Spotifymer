package cz.muni.pv239.spotifymer.view_model

import android.app.Application
import androidx.lifecycle.*
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.repository.PlaylistRepository
import cz.muni.pv239.spotifymer.util.RandomCover
import kotlinx.coroutines.launch


class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private var repository= PlaylistRepository(application)

    fun getPlaylists(): LiveData<List<Playlist>>? {
        return repository.getPlaylists()
    }

    fun getPlaylist(id: Long): LiveData<Playlist>? {
        return repository.getPlaylist(id)
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
                RandomCover.generate()
            )
        )
    }

    fun removePlaylist(playlist: Playlist) = viewModelScope.launch {
        repository.removePlaylist(playlist)
    }

    fun updatePlaylist(playlist: Playlist) = viewModelScope.launch {
        repository.updatePlaylist(playlist)
    }
}