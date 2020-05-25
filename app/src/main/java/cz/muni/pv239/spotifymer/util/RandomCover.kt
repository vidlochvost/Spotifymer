package cz.muni.pv239.spotifymer.util

object RandomCover {

    fun generate() = "https://picsum.photos/id/${(0..1000).random()}/150/150"
}