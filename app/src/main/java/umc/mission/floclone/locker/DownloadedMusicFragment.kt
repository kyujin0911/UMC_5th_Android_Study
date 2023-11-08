package umc.mission.floclone.locker

import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mission.floclone.R
import umc.mission.floclone.adapter.NewMusicDailyAdapter
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.DOWNLOADED_MUSIC
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.NEW_MUSIC_DAILY
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.PLAY_BTN
import umc.mission.floclone.data.Music
import umc.mission.floclone.databinding.FragmentDownloadedMusicBinding

class DownloadedMusicFragment: Fragment(), NewMusicDailyAdapter.ItemClickListener {
    private lateinit var binding: FragmentDownloadedMusicBinding
    private lateinit var musicList: MutableList<Music>
    private lateinit var newMusicDailyAdapter: NewMusicDailyAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadedMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        makeDummyData()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.fragmentDownloadedMusicRV
        recyclerView.layoutManager = layoutManager
        newMusicDailyAdapter = NewMusicDailyAdapter(DOWNLOADED_MUSIC, this)
        recyclerView.adapter = newMusicDailyAdapter
        newMusicDailyAdapter.submitList(musicList)
        val updateList = newMusicDailyAdapter.currentList.toMutableList()
        Log.d("ui", "$updateList")
    }

    override fun onClick(music: Music, viewType: Int) {
      /*  Log.d("music", "$music")
        val updateList = newMusicDailyAdapter.currentList.toMutableList().apply { add(Music(
            "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                    "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel"
        )) }
        newMusicDailyAdapter.submitList(updateList)*/

    }

    override fun onRemove(position: Int) {
        val currentList = newMusicDailyAdapter.currentList.toMutableList()
        currentList.removeAt(position)
        Log.d("position", "$position")
        newMusicDailyAdapter.submitList(currentList)

    }

    private fun makeDummyData() {
        musicList = mutableListOf(
            Music(
                "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                        "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel"
            ),
            Music(
                "작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4, "모든 게 궁금해\n" +
                        "How's your day", "2019.04.12 미니 알앤비, 힙합", 0, 229, false, "music_nextlevel"
            ),
            Music(
                "BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, "Bae Bae Bae BAAM BAAM\n" +
                        "Bae Bae Bae BAAM BAAM", "2018.06.26", 0, 208, false, "music_nextlevel"
            ),
            Music(
                "Weekend", "태연", R.drawable.img_album_exp6, "가장 가까운 바다\n" +
                        "혼자만의 영화관", "2021.07.06 싱글 댄스팝", 0, 234, false, "music_nextlevel"
            ),
            Music(
                "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                        "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel"
            ),
            Music(
                "작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4, "모든 게 궁금해\n" +
                        "How's your day", "2019.04.12 미니 알앤비, 힙합", 0, 229, false, "music_nextlevel"
            ),
            Music(
                "BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, "Bae Bae Bae BAAM BAAM\n" +
                        "Bae Bae Bae BAAM BAAM", "2018.06.26", 0, 208, false, "music_nextlevel"
            ),
            Music(
                "Weekend", "태연", R.drawable.img_album_exp6, "가장 가까운 바다\n" +
                        "혼자만의 영화관", "2021.07.06 싱글 댄스팝", 0, 234, false, "music_nextlevel"
            ),
            Music(
                "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                        "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel"
            ),
            Music(
                "작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4, "모든 게 궁금해\n" +
                        "How's your day", "2019.04.12 미니 알앤비, 힙합", 0, 229, false, "music_nextlevel"
            ),
            Music(
                "BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, "Bae Bae Bae BAAM BAAM\n" +
                        "Bae Bae Bae BAAM BAAM", "2018.06.26", 0, 208, false, "music_nextlevel"
            ),
            Music(
                "Weekend", "태연", R.drawable.img_album_exp6, "가장 가까운 바다\n" +
                        "혼자만의 영화관", "2021.07.06 싱글 댄스팝", 0, 234, false, "music_nextlevel"
            ),
            Music(
                "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                        "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel"
            ),
            Music(
                "작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4, "모든 게 궁금해\n" +
                        "How's your day", "2019.04.12 미니 알앤비, 힙합", 0, 229, false, "music_nextlevel"
            ),
            Music(
                "BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, "Bae Bae Bae BAAM BAAM\n" +
                        "Bae Bae Bae BAAM BAAM", "2018.06.26", 0, 208, false, "music_nextlevel"
            ),
            Music(
                "Weekend", "태연", R.drawable.img_album_exp6, "가장 가까운 바다\n" +
                        "혼자만의 영화관", "2021.07.06 싱글 댄스팝", 0, 234, false, "music_nextlevel"
            )
        )
    }
}