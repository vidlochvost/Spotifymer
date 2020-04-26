package cz.muni.pv239.spotifymer.model

import androidx.lifecycle.ViewModel

class PlaylistAttributes: ViewModel() {

    private val searches = ArrayList<Search>()

    private val tunableAttributes = TunableAttributes()

    fun getSearches(): ArrayList<Search> {
        return this.searches
    }

    fun addSearch(artist: Search) {
        this.searches.add(artist)
    }

    fun removeSearch(artist: Search) {
        this.searches.remove(artist)
    }

    fun setEnergy(value: Float) {
        tunableAttributes.energy.value = value
    }

    fun getEnergy(): Energy {
        return tunableAttributes.energy
    }
}