package cz.muni.pv239.spotifymer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val item = searchList?.get(position)

        if (item?.imgUrl == null) {
            holder.image.setImageResource(R.drawable.genre)
        } else {
            Picasso.get().load(item.imgUrl).into(holder.image)
        }
        holder.primaryText.text = item?.primaryText
        item?.secondaryText?.let { holder.secondaryText.text = it }
        holder.searchCard.setOnClickListener {
            if (item != null) {
                val result = searchViewModel?.addSearch(item)
                if (result == true) {
                    searchList?.remove(searchList[position])
                    Toast.makeText(it.context, "Added!", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(it.context, "Reached search maximum!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

class SearchCardHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.result_image)
    val primaryText = view.findViewById<TextView>(R.id.primary_text)
    val secondaryText = view.findViewById<TextView>(R.id.secondary_text)
    val searchCard = view.findViewById<MaterialCardView>(R.id.search_card)
}