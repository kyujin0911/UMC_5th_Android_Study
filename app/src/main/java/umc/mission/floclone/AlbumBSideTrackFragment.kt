package umc.mission.floclone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.adapter.NewMusicDailyAdapter
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.B_SIDE_TRACK
import umc.mission.floclone.databinding.FragmentAlbumBSideTrackBinding

class AlbumBSideTrackFragment: Fragment() {
    private lateinit var binding: FragmentAlbumBSideTrackBinding
    private var musicTitle: String? = null
    private var musicSinger: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBSideTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initRecyclerView()
        parentFragmentManager.setFragmentResultListener("music", this){ _, bundle ->
            musicTitle = bundle.getString("music_title").toString()
            musicSinger = bundle.getString("music_singer").toString()
        }
        Log.d("Bside", musicTitle.toString())
        val list = mutableListOf(Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger))
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = view?.findViewById<RecyclerView>(R.id.album_b_side_track_recyclerview)
        recyclerView?.layoutManager = layoutManager
        binding.albumBSideTrackRecyclerview.adapter = NewMusicDailyAdapter(list, B_SIDE_TRACK)
    }

    /*private fun initRecyclerView(){
        val musicTitle = arguments?.getString("music_title")
        val musicSinger = arguments?.getString("music_singer")

        val list = mutableListOf(Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger),
            Music(musicTitle, musicSinger))
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = view?.findViewById<RecyclerView>(R.id.album_b_side_track_recyclerview)
        recyclerView?.layoutManager = layoutManager
        binding.albumBSideTrackRecyclerview.adapter = NewMusicDailyAdapter(list, B_SIDE_TRACK)
    }*/
}