package cz.muni.pv239.spotifymer.model

import cz.muni.pv239.spotifymer.util.SearchType

class Search(
    val imgUrl: String?,
    val primaryText: String,
    val secondaryText: String?,
    val id: String,
    val type: SearchType)