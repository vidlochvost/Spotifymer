package cz.muni.pv239.spotifymer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Song
import cz.muni.pv239.spotifymer.view.songs_overview.SongOverviewFragment

class SongListAdapter(
    private val fragmentManager: FragmentManager,
    private val songs: List<Song>?
) :
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
        holder.songAuthor.text = songs?.get(position)?.author
        holder.card.setOnClickListener {
            if (songs?.get(position) != null) {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.songs_content, SongOverviewFragment(songs[position]))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}

class SongCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val coverImage = view.findViewById<ImageView>(R.id.song_image)
    val songName = view.findViewById<TextView>(R.id.song_title_text)
    val songAuthor = view.findViewById<TextView>(R.id.song_author_text)
    val button = view.findViewById<Button>(R.id.song_button)
    val card =
        view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.song_card)
}