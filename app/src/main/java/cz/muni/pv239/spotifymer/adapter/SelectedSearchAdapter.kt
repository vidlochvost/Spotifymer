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

class SelectedSearchAdapter(
    private val searchList: List<Search>?,
    private val searchViewModel: SearchViewModel?
) :
    RecyclerView.Adapter<SelectedSearchCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSearchCardHolder {
        val layout = R.layout.search_attribute_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return SelectedSearchCardHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList?.size!!
    }

    override fun onBindViewHolder(holder: SelectedSearchCardHolder, position: Int) {
        if (searchList?.get(position)?.imgUrl == null) {
            holder.image.setImageResource(R.drawable.genre)
        } else {
            Picasso.get().load(searchList[position].imgUrl).into(holder.image)
        }
        holder.title.text = searchList?.get(position)?.title
        holder.type.text = searchList?.get(position)?.type?.name
        holder.removeButton.setOnClickListener {
            searchViewModel?.removeSearch(searchList?.get(position)!!)
        }
    }
}

class SelectedSearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.search_result_image)
    val title = view.findViewById<TextView>(R.id.search_title_text)
    val type = view.findViewById<TextView>(R.id.search_type_text)
    val removeButton = view.findViewById<Button>(R.id.search_remove_button)
}