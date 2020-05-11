package cz.muni.pv239.spotifymer.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.models.Track
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.model.TunableAttributes
import cz.muni.pv239.spotifymer.repository.SearchRepository
import cz.muni.pv239.spotifymer.util.SearchType
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private var repository = SearchRepository()

    private var searchList = ArrayList<Search>()

    private val attributes = TunableAttributes()

    fun getSearches(): LiveData<List<Search>> {
        return MutableLiveData(searchList)
    }

    fun getAttributes() : TunableAttributes {
        return attributes
    }

    fun addSearch(search: Search) {
        if (searchList.size == 5) {
            return
        }
        searchList.add(search)
    }

    fun removeSearch(artist: Search) {
        searchList.remove(artist)
    }

    fun search(pattern: String, type: SearchType) : LiveData<List<Search>> {
        val result = MutableLiveData<List<Search>>()
        viewModelScope.launch {
            result.postValue( when (type) {
                SearchType.ARTIST -> repository.searchArtists(pattern)
                SearchType.TRACK -> repository.searchTracks(pattern)
                SearchType.GENRE -> repository.searchGenres(pattern)
            })
        }

        return result
    }

    fun getRecommendations(): LiveData<List<Track>> {
        if (searchList.isEmpty()) {
            throw Throwable("Not enough search parameters")
        }

        val result = MutableLiveData<List<Track>>()
        viewModelScope.launch {
            val tracks = repository.getRecommendations(searchList)
            result.postValue(tracks)
        }
        return result
    }
}