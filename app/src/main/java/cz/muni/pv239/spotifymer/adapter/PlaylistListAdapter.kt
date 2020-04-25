package cz.muni.pv239.spotifymer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Playlist

class PlaylistListAdapter (private val playlists: List<Playlist>) :
    RecyclerView.Adapter<PlaylistCardHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistCardHolder {
        return PlaylistCardHolder(LayoutInflater.from(parent.context).inflate(R.layout.playlist_card, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistCardHolder, position: Int) {
        //loading image from url using picasso
        Picasso.get().load(playlists[position].imageUrl).into(holder.coverImage)
        holder.playlistNme.text = playlists[position].name
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}


class PlaylistCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val coverImage = view.findViewById<ImageView>(R.id.playlist_cover_image)
    val playlistNme = view.findViewById<TextView>(R.id.playlist_name)
}