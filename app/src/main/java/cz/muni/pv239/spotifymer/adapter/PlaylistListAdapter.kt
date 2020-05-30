package cz.muni.pv239.spotifymer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Playlist
import cz.muni.pv239.spotifymer.view.songs_overview.SongsOverviewActivity
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel


class PlaylistListAdapter(
    private var playlists: ArrayList<Playlist>?,
    private val playlistViewModel: PlaylistViewModel?
) : RecyclerView.Adapter<PlaylistCardHolder>(), CardAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistCardHolder {
        return PlaylistCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return playlists?.size!!
    }

    override fun onBindViewHolder(holder: PlaylistCardHolder, position: Int) {
        val playlist = playlists?.get(position)
        //loading image from url using picasso
        Picasso.get().load(playlist?.imageUrl).into(holder.coverImage)
        holder.playlistName.text = playlist?.name
        holder.card.setOnClickListener { view ->
            val id = playlist?.id
            if (id != null) {
                val intent = Intent(view.context, SongsOverviewActivity::class.java)
                intent.putExtra("PLAYLIST_ID", id)
                view.context.startActivity(intent)
            }
        }
    }

    override fun removeItem(position: Int) {
        val item = playlists?.get(position)
        if (item != null) {
            playlistViewModel?.removePlaylist(item)
            playlists?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun renderDataset(p: ArrayList<Playlist>) {
        playlists = p
        notifyDataSetChanged()
    }
}


class PlaylistCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val coverImage = view.findViewById<ImageView>(R.id.playlist_cover_image)
    val playlistName = view.findViewById<TextView>(R.id.playlist_name)
    val card =
        view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.playlist_card_view)
}