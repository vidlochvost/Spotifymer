package cz.muni.pv239.spotifymer.model

import com.adamratzman.spotify.endpoints.public.TuneableTrackAttribute

interface Attribute {
    var value: Float
    var isActive: Boolean

    fun getType(): TuneableTrackAttribute<Float>
}

class Danceability : Attribute {
    private val type = TuneableTrackAttribute.Danceability
    override var value = 0.5f
    override var isActive = false

    override fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Valence : Attribute {
    private val type = TuneableTrackAttribute.Valence
    override var value: Float = 0.5f
    override var isActive = false

    override fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Energy : Attribute {
    private val type = TuneableTrackAttribute.Energy
    override var value: Float = 0.5f
    override var isActive = false

    override fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}

class Tempo : Attribute {
    private val type = TuneableTrackAttribute.Tempo
    override var value: Float = 125f
    override var isActive = false

    override fun getType(): TuneableTrackAttribute<Float> {
        return type
    }
}