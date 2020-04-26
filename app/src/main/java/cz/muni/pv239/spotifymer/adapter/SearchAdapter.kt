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

class SearchAdapter(private var results: List<Search>, private var model: PlaylistAttributes) :
    RecyclerView.Adapter<SearchCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCardHolder {
        return SearchCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: SearchCardHolder, position: Int) {
        if (results[position].imgUrl == null) {
            holder.image.setImageResource(R.drawable.genre)
        } else {
            Picasso.get().load(results[position].imgUrl).into(holder.image)
        }
        holder.title.text = results[position].title
        holder.addButton.setOnClickListener {
            model.addSearch(results[position])
        }
    }

    fun changeData(newData: List<Search>) {
        results = newData
        notifyDataSetChanged()
    }
}

class SearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.result_image)
    val title = view.findViewById<TextView>(R.id.title_text)
    val addButton = view.findViewById<Button>(R.id.add_button)
}