package cz.muni.pv239.spotifymer.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "tracks",
    indices = [Index("playlist_id")],
    foreignKeys = [ForeignKey(
        entity = Playlist::class,
        parentColumns = ["id"],
        childColumns = ["playlist_id"],
        onDelete = CASCADE
    )]
)
data class Song(
    @ColumnInfo(name = "playlist_id") var playlistId: Long,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "author") var author: String,
    @ColumnInfo(name = "spotify_url") var spotifyUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}