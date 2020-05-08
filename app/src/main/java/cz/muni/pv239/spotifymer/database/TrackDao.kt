package cz.muni.pv239.spotifymer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.muni.pv239.spotifymer.model.Song

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    fun getAll(): LiveData<List<Song>>

    @Query("SELECT * FROM tracks WHERE playlist_id = :playlistId")
    fun getAll(playlistId: Long): LiveData<List<Song>>

    @Insert
    suspend fun insert(song: Song)
}