package cz.muni.pv239.spotifymer.activity.SearchMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SearchAdapter
import cz.muni.pv239.spotifymer.adapter.SearchAttributesListAdapter
import cz.muni.pv239.spotifymer.model.PlaylistAttributes

class RecommendMenuFragment : Fragment() {

    lateinit var tempButton: MaterialButton
    lateinit var searchAttributeRecyclerView: RecyclerView
    lateinit var seekbar: SeekBar
    lateinit var energyTextView: TextView

    private val model: PlaylistAttributes by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        energyTextView = view.findViewById(R.id.energy_text)

        searchAttributeRecyclerView = view.findViewById(R.id.search_attributes_recycler_view)
        searchAttributeRecyclerView.layoutManager = LinearLayoutManager(view.context)
        val adapter = SearchAttributesListAdapter(model)
        searchAttributeRecyclerView.adapter = adapter

        seekbar = view.findViewById(R.id.energy_seekbar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val energy = i/100.0f
                energyTextView.text = energy.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val energy = seekBar.progress/100.0f
                model.setEnergy(energy)
            }
        })

        tempButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.content_layout, SearchMenuFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommend_menu, container, false)
        tempButton = view.findViewById(R.id.temp_button)
        return view
    }
}
