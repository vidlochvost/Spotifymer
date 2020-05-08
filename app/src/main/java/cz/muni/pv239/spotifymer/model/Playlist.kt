package cz.muni.pv239.spotifymer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "playlists")
data class Playlist(
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "image_url") var imageUrl: String?
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0

}