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
import cz.muni.pv239.spotifymer.model.SearchResult

class SearchResultsAdapter (private var results: List<SearchResult>, private val context: Context) :
    RecyclerView.Adapter<SearchResultsCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsCardHolder {
        return SearchResultsCardHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false))
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: SearchResultsCardHolder, position: Int) {
        Picasso.get().load(results[position].imgUrl).into(holder.image)
        holder.title.text = results[position].title
    }

    fun changeData(newData: List<SearchResult>) {
        results = newData
        notifyDataSetChanged()
    }
}

class SearchResultsCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.result_image)
    val title = view.findViewById<TextView>(R.id.title_text)
}