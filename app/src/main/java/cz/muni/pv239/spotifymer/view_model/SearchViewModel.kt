package cz.muni.pv239.spotifymer.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.models.Track
import cz.muni.pv239.spotifymer.model.*
import cz.muni.pv239.spotifymer.repository.SearchRepository
import cz.muni.pv239.spotifymer.util.AttributeType
import cz.muni.pv239.spotifymer.util.SearchType
import kotlinx.coroutines.launch
import org.w3c.dom.Attr

class SearchViewModel : ViewModel() {

    private var repository = SearchRepository()

    private var searchList = ArrayList<Search>()

    private val attributes = mapOf(
        Pair(AttributeType.ENERGY, Energy()),
        Pair(AttributeType.TEMPO, Tempo()),
        Pair(AttributeType.DANCEABILITY, Danceability()),
        Pair(AttributeType.VALENCE, Valence())
    )

    fun getSearches(): LiveData<List<Search>> {
        return MutableLiveData(searchList)
    }

    fun getAttributes(): Map<AttributeType, Attribute> {
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

    fun search(pattern: String, type: SearchType): LiveData<List<Search>> {
        val result = MutableLiveData<List<Search>>()
        viewModelScope.launch {
            result.postValue(
                when (type) {
                    SearchType.ARTIST -> repository.searchArtists(pattern)
                    SearchType.TRACK -> repository.searchTracks(pattern)
                    SearchType.GENRE -> repository.searchGenres(pattern)
                }
            )
        }

        return result
    }

    fun getRecommendations(): LiveData<List<Track>> {
        if (searchList.isEmpty()) {
            throw Throwable("Not enough search parameters")
        }

        val result = MutableLiveData<List<Track>>()
        viewModelScope.launch {
            val tracks = repository.getRecommendations(
                searchList,
                attributes.filter { it.value.isActive }.map { it.value })
            result.postValue(tracks)
        }
        return result
    }
}