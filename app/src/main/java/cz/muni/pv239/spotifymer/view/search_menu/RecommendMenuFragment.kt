package cz.muni.pv239.spotifymer.view.search_menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamratzman.spotify.SpotifyAppApi

import cz.muni.pv239.spotifymer.R
import cz.muni.pv239.spotifymer.adapter.SearchAttributesListAdapter
import cz.muni.pv239.spotifymer.databinding.FragmentRecommendMenuBinding
import cz.muni.pv239.spotifymer.model.PlaylistAttributes
import cz.muni.pv239.spotifymer.util.SpotifyWebApi
import cz.muni.pv239.spotifymer.view_model.PlaylistViewModel
import cz.muni.pv239.spotifymer.view_model.TrackViewModel

class RecommendMenuFragment : Fragment() {

    private lateinit var api: SpotifyAppApi

    private var _binding: FragmentRecommendMenuBinding? = null
    private val binding get() = _binding!!

    private var playlistViewModel: PlaylistViewModel? = null
    private var trackViewModel: TrackViewModel? = null

    private val model: PlaylistAttributes by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        api = SpotifyWebApi.getInstance()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        trackViewModel = ViewModelProvider(requireActivity()).get(TrackViewModel::class.java)

        this.intiRecyclerView(view.context)
        this.intiEnergySeekBar()

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
                        this.trackViewModel?.bindTracksToPlaylist(id, model)
                        activity?.finish()
                    })

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

    private fun intiRecyclerView(context: Context) {
        binding.searchAttributesRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SearchAttributesListAdapter(model)
        binding.searchAttributesRecyclerView.adapter = adapter
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
                model.setEnergy(energy)
            }
        })
    }
}
