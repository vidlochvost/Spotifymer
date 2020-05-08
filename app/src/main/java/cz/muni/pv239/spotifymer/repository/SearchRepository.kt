package cz.muni.pv239.spotifymer.repository

import com.adamratzman.spotify.endpoints.public.TrackAttribute
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.util.SearchType
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SearchRepository {

    private val spotifyApi = SpotifyWebApi.getInstance()

    suspend fun searchArtists(pattern: String) = withContext(IO) {
        spotifyApi.search.searchArtist(pattern).complete()
            .map {
                Search(
                    it.images.getOrNull(2)?.url,
                    it.name,
                    it.id,
                    SearchType.ARTIST
                )
            }
    }

    suspend fun searchTracks(pattern: String) = withContext(IO) {
        spotifyApi.search.searchTrack(pattern).complete()
            .map {
                Search(
                    it.album.images.getOrNull(2)?.url,
                    it.name,
                    it.id,
                    SearchType.TRACK
                )
            }
    }

    suspend fun searchGenres(pattern: String) = withContext(IO) {
        spotifyApi.browse.getAvailableGenreSeeds().complete()
            .filter { it.contains(pattern) }
            .map { Search(null, it, it, SearchType.GENRE) }
    }

    suspend fun getRecommendations(
        searchList: ArrayList<Search>
    ) = withContext(IO) {
        spotifyApi.browse.getTrackRecommendations(
            seedArtists = searchList.filter { it.type == SearchType.ARTIST }.map { it.id },
            seedTracks = searchList.filter { it.type == SearchType.TRACK }.map { it.id },
            seedGenres = searchList.filter { it.type == SearchType.GENRE }.map { it.id }
        ).complete().tracks
    }
}