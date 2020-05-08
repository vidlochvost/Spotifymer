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
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.view_model.SearchViewModel

class SearchAdapter(
    private val searchList: List<Search>?,
    private val searchViewModel: SearchViewModel?
) :
    RecyclerView.Adapter<SearchCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCardHolder {
        return SearchCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return searchList?.size!!
    }

    override fun onBindViewHolder(holder: SearchCardHolder, position: Int) {
        if (searchList?.get(position)?.imgUrl == null) {
            holder.image.setImageResource(R.drawable.genre)
        } else {
            Picasso.get().load(searchList[position].imgUrl).into(holder.image)
        }
        holder.title.text = searchList?.get(position)?.title
        holder.addButton.setOnClickListener {
            searchViewModel?.addSearch(searchList?.get(position)!!)
        }
    }
}

class SearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.result_image)
    val title = view.findViewById<TextView>(R.id.title_text)
    val addButton = view.findViewById<Button>(R.id.add_button)
}