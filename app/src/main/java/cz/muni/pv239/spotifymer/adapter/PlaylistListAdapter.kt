package cz.muni.pv239.spotifymer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.view.songs_overview.SongsOverviewActivity
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel

class PlaylistListAdapter(
    private val playlists: List<Playlist>?,
    private val playlistViewModel: PlaylistViewModel?
) :
    RecyclerView.Adapter<PlaylistCardHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistCardHolder {
        return PlaylistCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return playlists?.size!!
    }

    override fun onBindViewHolder(holder: PlaylistCardHolder, position: Int) {
        //loading image from url using picasso
        Picasso.get().load(playlists?.get(position)?.imageUrl).into(holder.coverImage)
        holder.playlistName.text = playlists?.get(position)?.name
        val id = playlists?.get(position)?.id

        holder.button.setOnClickListener {
            if (id != null) {
                //val songs = trackViewModel?.getTracksByPlaylist(id)
                //songs?.map { song -> println("Song: ${song.name}") }
                playlistViewModel?.removePlaylist(id)
            }
        }
        holder.card.setOnClickListener { view ->
            if (id != null) {
                val intent = Intent(view.context, SongsOverviewActivity::class.java)
                intent.putExtra("PLAYLIST_ID", id)
                view.context.startActivity(intent)
            }
        }
    }
}


class PlaylistCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val coverImage = view.findViewById<ImageView>(R.id.playlist_cover_image)
    val playlistName = view.findViewById<TextView>(R.id.playlist_name)
    val button = view.findViewById<Button>(R.id.temp_func_button)
    val card =
        view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.playlist_card_view)
}