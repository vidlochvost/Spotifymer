package cz.muni.pv239.spotifymer.model

import com.adamratzman.spotify.endpoints.public.TuneableTrackAttribute

class TunableAttributes {

    val energy = Energy()

    val tempo = Tempo()

    val danceability = Danceability()

    val valence = Valence()
}

class Danceability {
    private val type = TuneableTrackAttribute.Danceability
    var value: Float = 0.5f

    fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Valence {
    private val type = TuneableTrackAttribute.Valence
    var value: Float = 0.5f

    fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Energy {
    private val type = TuneableTrackAttribute.Energy
    var value: Float = 0.5f

    fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Tempo {
    private val type = TuneableTrackAttribute.Tempo
    var value: Float = 125f

    fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}