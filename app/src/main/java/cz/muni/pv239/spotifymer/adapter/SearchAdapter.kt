package cz.muni.pv239.spotifymer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.view_model.SearchViewModel

class SearchAdapter(
    private val searchList: ArrayList<Search>?,
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
        holder.primaryText.text = searchList?.get(position)?.primaryText
        searchList?.get(position)?.secondaryText?.let { holder.secondaryText.text = it }
        holder.searchCard.setOnClickListener {
            searchList?.get(position)?.let { it1 -> searchViewModel?.addSearch(it1) }
            searchList?.remove(searchList[position])
            notifyDataSetChanged()
        }
    }
}

class SearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.result_image)
    val primaryText = view.findViewById<TextView>(R.id.primary_text)
    val secondaryText = view.findViewById<TextView>(R.id.secondary_text)
    val searchCard = view.findViewById<MaterialCardView>(R.id.search_card)
}