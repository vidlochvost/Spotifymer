package cz.muni.pv239.spotifymer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Song

class SongListAdapter(private val songs: List<Song>?) :
    RecyclerView.Adapter<SongCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongCardHolder {
        return SongCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.song_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return songs?.size!!
    }

    override fun onBindViewHolder(holder: SongCardHolder, position: Int) {
        Picasso.get().load(songs?.get(position)?.imageUrl).into(holder.coverImage)
        holder.songName.text = songs?.get(position)?.name
    }
}

class SongCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val coverImage = view.findViewById<ImageView>(R.id.song_image)
    val songName = view.findViewById<TextView>(R.id.song_title_text)
    val button = view.findViewById<Button>(R.id.song_button)
    val card =
        view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.song_card)
}