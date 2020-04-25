package cz.muni.pv239.spotifymer.model

import com.adamratzman.spotify.endpoints.public.TuneableTrackAttribute

class TunableAttributes {

    val energy = Energy()
}

class Energy {
    private val type = TuneableTrackAttribute.Energy
    var value: Float = 0.5f

    fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}