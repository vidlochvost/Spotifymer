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
    private var searchList: List<Search>?,
    private val searchViewModel: SearchViewModel?
) :
    RecyclerView.Adapter<SelectedSearchCardHolder>(), CardAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSearchCardHolder {
        val layout = R.layout.search_attribute_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return SelectedSearchCardHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList?.size!!
    }

    override fun onBindViewHolder(holder: SelectedSearchCardHolder, position: Int) {
        val item = searchList?.get(position)
        if (item?.imgUrl == null) {
            holder.image.setImageResource(R.drawable.genre)
        } else {
            Picasso.get().load(item.imgUrl).into(holder.image)
        }
        holder.title.text = item?.primaryText
        holder.type.text = item?.type?.name
    }

    override fun removeItem(position: Int) {
        searchList?.get(position)?.let { searchViewModel?.removeSearch(it) }
        notifyItemRemoved(position)
    }

    fun renderDataset(s: List<Search>) {
        searchList = s
        notifyDataSetChanged()
    }
}

class SelectedSearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.search_result_image)
    val title = view.findViewById<TextView>(R.id.search_title_text)
    val type = view.findViewById<TextView>(R.id.search_type_text)
}