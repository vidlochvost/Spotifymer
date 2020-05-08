package cz.muni.pv239.spotifymer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity( tableName = "tracks",
    foreignKeys = [ForeignKey(
    entity = Playlist::class,
    parentColumns = ["id"],
    childColumns = ["playlist_id"],
    onDelete = CASCADE)])
data class Song (
    @ColumnInfo(name = "playlist_id") var playlistId: Long,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "spotify_url") var spotifyUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}