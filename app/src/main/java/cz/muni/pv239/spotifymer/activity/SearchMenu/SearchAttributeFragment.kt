package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton

import cz.muni.pv239.spotifymer.R

class SearchAttributeFragment : Fragment() {

    lateinit var tempButton: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempButton.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content_layout, SearchFragment())
                ?.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_attribute, container, false)
        tempButton = view.findViewById(R.id.temp_button)
        return view
    }
}
