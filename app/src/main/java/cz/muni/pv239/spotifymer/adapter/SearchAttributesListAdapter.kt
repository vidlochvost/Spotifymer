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
import cz.muni.pv239.spotifymer.model.PlaylistAttributes
import cz.muni.pv239.spotifymer.model.Search

class SearchAttributesListAdapter(private var model: PlaylistAttributes) :
    RecyclerView.Adapter<SearchAttributesCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAttributesCardHolder {
        val layout = R.layout.search_attribute_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return SearchAttributesCardHolder(view)
    }

    override fun getItemCount(): Int {
        return model.getSearches().size
    }

    override fun onBindViewHolder(holder: SearchAttributesCardHolder, position: Int) {
        Picasso.get().load(model.getSearches()[position].imgUrl).into(holder.image)
        holder.title.text = model.getSearches()[position].title
        holder.type.text = model.getSearches()[position].type
        holder.removeButton.setOnClickListener {
            this.removeArtist(model.getSearches()[position])
        }
    }

    private fun removeArtist(artist: Search) {
        model.removeSearch(artist)
        model.getSearches().remove(artist)
        notifyDataSetChanged()
    }
}

class SearchAttributesCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.search_result_image)
    val title = view.findViewById<TextView>(R.id.search_title_text)
    val type = view.findViewById<TextView>(R.id.search_type_text)
    val removeButton = view.findViewById<Button>(R.id.search_remove_button)
}