package umc.mission.floclone

import android.os.Bundle
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBSideTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = mutableListOf(NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3))
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = view.findViewById<RecyclerView>(R.id.album_b_side_track_recyclerview)
        recyclerView.layoutManager = layoutManager
        binding.albumBSideTrackRecyclerview.adapter = NewMusicDailyAdapter(list, B_SIDE_TRACK)
    }
}