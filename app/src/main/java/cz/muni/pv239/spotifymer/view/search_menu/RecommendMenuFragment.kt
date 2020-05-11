package cz.muni.pv239.spotifymer.view.search_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SelectedSearchAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentRecommendMenuBinding
import cz.muni.pv239.spotifymer.model.Search
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

        initEnergySeekBar()
        initDanceabilitySeekBar()
        initTempoSeekBar()
        initValenceSeekBar()

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

    private fun initEnergySeekBar() {
        binding.energySeekbar.progress = 50
        binding.energySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val energy = i / 100.0f
                binding.energyText.text = energy.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val energy = seekBar.progress / 100.0f
                searchViewModel?.getAttributes()?.energy?.value = energy
            }
        })
        binding.energyChip.setOnClickListener {
            if (binding.energyChip.isChecked) {
                binding.energyLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                binding.energyLayout.layoutParams.height = 0
            }
            binding.energyLayout.requestLayout();
        }
    }

    private fun initTempoSeekBar() {
        binding.tempoSeekbar.progress = 125
        binding.tempoSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding.tempoText.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val tempo = seekBar.progress.toFloat()
                searchViewModel?.getAttributes()?.tempo?.value = tempo
            }
        })
        binding.tempoChip.setOnClickListener {
            if (binding.tempoChip.isChecked) {
                binding.tempoLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                binding.tempoLayout.layoutParams.height = 0
            }
            binding.tempoLayout.requestLayout();
        }
    }

    private fun initDanceabilitySeekBar() {
        binding.danceabilitySeekbar.progress = 50
        binding.danceabilitySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val danceability = i / 100.0f
                binding.danceabilityText.text = danceability.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val danceability = seekBar.progress / 100.0f
                searchViewModel?.getAttributes()?.danceability?.value = danceability
            }
        })
        binding.danceabilityChip.setOnClickListener {
            if (binding.danceabilityChip.isChecked) {
                binding.danceabilityLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                binding.danceabilityLayout.layoutParams.height = 0
            }
            binding.danceabilityLayout.requestLayout();
        }
    }

    private fun initValenceSeekBar() {
        binding.valenceSeekbar.progress = 50
        binding.valenceSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val valence = i / 100.0f
                binding.valenceText.text = valence.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val valence = seekBar.progress / 100.0f
                searchViewModel?.getAttributes()?.valence?.value = valence
            }
        })
        binding.valenceChip.setOnClickListener {
            if (binding.valenceChip.isChecked) {
                binding.valenceLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                binding.valenceLayout.layoutParams.height = 0
            }
            binding.valenceLayout.requestLayout();
        }
    }
}
