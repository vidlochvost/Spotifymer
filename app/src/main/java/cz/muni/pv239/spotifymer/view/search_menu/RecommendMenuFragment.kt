package cz.muni.pv239.spotifymer.view.search_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SelectedSearchAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentRecommendMenuBinding
import cz.muni.pv239.spotifymer.model.Attribute
import cz.muni.pv239.spotifymer.model.Search
import cz.muni.pv239.spotifymer.util.AttributeType
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.SearchViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel

class RecommendMenuFragment : Fragment() {

    private var _binding: FragmentRecommendMenuBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null
    private var searchViewModel: SearchViewModel? = null

    lateinit var adapter: SelectedSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        trackViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

        initChips()
        initSeekBars()
        renderRecyclerView(listOf())

        searchViewModel
            ?.getSearches()
            ?.observe(viewLifecycleOwner, Observer { renderRecyclerView(it) })

        binding.addSearchButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.content_layout, SearchMenuFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.searchRecommendationsButton.setOnClickListener {
            searchViewModel?.getSearches()?.observe(
                viewLifecycleOwner,
                Observer { searches ->
                    if (searches.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Search for artists, tracks or genres!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        searchViewModel?.getRecommendations()?.observe(
                            viewLifecycleOwner,
                            Observer { tracks ->
                                if (tracks.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Sorry, not enough parameters provided :(",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    playlistViewModel?.newPlaylist(binding.albumNameInput.text.toString())
                                        ?.observe(
                                            viewLifecycleOwner,
                                            Observer { id ->
                                                trackViewModel?.bindTracksToPlaylist(
                                                    id,
                                                    tracks
                                                )
                                            })
                                    activity?.finish()
                                }

                            })
                    }
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        this.setLayout(binding.energyChip, binding.energyLayout)
        this.setLayout(binding.tempoChip, binding.tempoLayout)
        this.setLayout(binding.danceabilityChip, binding.danceabilityLayout)
        this.setLayout(binding.valenceChip, binding.valenceLayout)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderRecyclerView(searchList: List<Search>?) {
        adapter = SelectedSearchAdapter(searchList, searchViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.selectedSearchRecyclerView.layoutManager = layoutManager
        binding.selectedSearchRecyclerView.adapter = adapter
    }

    private fun initSeekBar(
        seekBar: SeekBar,
        initProgress: Int,
        division: Float?,
        textView: TextView,
        attribute: Attribute?
    ) {
        seekBar.progress = initProgress
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                textView.text = calculate(i).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                attribute?.value = calculate(seekBar.progress).toFloat()
            }

            private fun calculate(i: Int): Number {
                val result: Number
                if (division == null) {
                    result = i
                } else {
                    result = (i / division)
                }
                return result
            }
        })
    }

    private fun initChip(chip: Chip, layout: LinearLayout, attribute: Attribute?) {
        chip.setOnClickListener {
            attribute?.isActive = chip.isChecked
            setLayout(chip, layout)
        }
    }

    private fun setLayout(chip: Chip, layout: LinearLayout) {
        if (chip.isChecked) {
            layout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layout.layoutParams.height = 0
        }
        layout.requestLayout()
    }

    private fun initChips() {
        initChip(
            binding.energyChip, binding.energyLayout,
            searchViewModel?.getAttributes()?.get(AttributeType.ENERGY)
        )
        initChip(
            binding.tempoChip, binding.tempoLayout,
            searchViewModel?.getAttributes()?.get(AttributeType.TEMPO)
        )
        initChip(
            binding.danceabilityChip, binding.danceabilityLayout,
            searchViewModel?.getAttributes()?.get(AttributeType.DANCEABILITY)
        )
        initChip(
            binding.valenceChip, binding.valenceLayout,
            searchViewModel?.getAttributes()?.get(AttributeType.VALENCE)
        )
    }

    private fun initSeekBars() {
        initSeekBar(
            binding.energySeekbar, 50, 100f, binding.energyText,
            searchViewModel?.getAttributes()?.get(AttributeType.ENERGY)
        )
        initSeekBar(
            binding.tempoSeekbar, 125, null, binding.tempoText,
            searchViewModel?.getAttributes()?.get(AttributeType.TEMPO)
        )
        initSeekBar(
            binding.danceabilitySeekbar, 50, 100f, binding.danceabilityText,
            searchViewModel?.getAttributes()?.get(AttributeType.DANCEABILITY)
        )
        initSeekBar(
            binding.valenceSeekbar, 50, 100f, binding.valenceText,
            searchViewModel?.getAttributes()?.get(AttributeType.VALENCE)
        )
    }
}
