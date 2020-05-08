package cz.muni.pv239.spotifymer.view.search_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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

        this.intiEnergySeekBar()

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
            this.playlistViewModel
                ?.newPlaylist(binding.albumNameInput.text.toString())
                ?.observe(
                    viewLifecycleOwner,
                    Observer { id ->
                        searchViewModel?.getRecommendations()?.observe(
                            viewLifecycleOwner,
                            Observer { tracks ->
                                this.trackViewModel?.bindTracksToPlaylist(
                                    id,
                                    tracks
                                )
                            })

                    })
            activity?.finish()
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

    private fun intiEnergySeekBar() {
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
                //TODO model.setEnergy(energy)
            }
        })
    }

    private fun renderRecyclerView(searchList: List<Search>?) {
        adapter = SelectedSearchAdapter(searchList, searchViewModel)
        val layoutManager = LinearLayoutManager(this.context)
        binding.selectedSearchRecyclerView.layoutManager = layoutManager
        binding.selectedSearchRecyclerView.adapter = adapter
    }
}
