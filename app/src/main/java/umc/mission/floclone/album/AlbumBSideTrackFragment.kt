package umc.mission.floclone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.data.Music
import umc.mission.floclone.R
import umc.mission.floclone.adapter.NewMusicDailyAdapter
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.B_SIDE_TRACK
import umc.mission.floclone.data.MUSIC_SINGER
import umc.mission.floclone.data.MUSIC_TITLE
import umc.mission.floclone.databinding.FragmentAlbumBSideTrackBinding

class AlbumBSideTrackFragment: Fragment() {
    private lateinit var binding: FragmentAlbumBSideTrackBinding
    private var musicTitle: String? = null
    private var musicSinger: String? = null
    private var toggle = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBSideTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        musicTitle = arguments?.getString(MUSIC_TITLE)
        musicSinger = arguments?.getString(MUSIC_SINGER)
        initRecyclerView()
        updateView()
    }

    private fun initRecyclerView(){
        val list = mutableListOf(
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger)
        )
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.albumBSideTrackRecyclerview
        recyclerView.layoutManager = layoutManager
        binding.albumBSideTrackRecyclerview.adapter = NewMusicDailyAdapter(B_SIDE_TRACK).apply {
            submitList(list)
        }

    }

    private fun updateView(){
        binding.albumBSideTrackMixBtn.setOnClickListener {
            toggle = !toggle
            binding.albumBSideTrackMixBtn.setImageResource(
                if (toggle) R.drawable.btn_toggle_on else R.drawable.btn_toggle_off
            )
        }
    }
}