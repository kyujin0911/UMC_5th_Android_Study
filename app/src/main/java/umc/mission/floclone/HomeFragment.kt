package umc.mission.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.NEW_MUSIC_DAILY
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.VIDEO_COLLECTION
import umc.mission.floclone.adapter.HomeViewPagerAdapter
import umc.mission.floclone.adapter.HomeViewPagerAdapter.Companion.ADD
import umc.mission.floclone.adapter.NewMusicDailyAdapter
import umc.mission.floclone.adapter.NewMusicDailyAdapter.Companion.PLAY_BTN
import umc.mission.floclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), NewMusicDailyAdapter.ItemClickListener {
    private lateinit var newMusicDailyAdapter: NewMusicDailyAdapter
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var musicList: MutableList<Music>
    private lateinit var podcastList: MutableList<Music>
    private lateinit var videoCollectionList: MutableList<Music>
    private lateinit var homeViewPager1List: MutableList<Int>
    private lateinit var homeViewPager2List: MutableList<Int>
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeDummyData()
        initRecyclerView(view, R.id.home_new_music_daily_recyclerview, NEW_MUSIC_DAILY)
        initRecyclerView(view, R.id.home_podcast_recyclerview, NEW_MUSIC_DAILY)
        initRecyclerView(view, R.id.home_video_collection_recyclerview, VIDEO_COLLECTION)
        initViewPager()
        checkNewMusicDailyCategory()
    }

    private fun makeDummyData(){
        musicList = mutableListOf(
            Music("Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                    "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝"),
            Music("작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4, "모든 게 궁금해\n" +
                    "How's your day", "2019.04.12 미니 알앤비, 힙합"),
            Music("BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, "Bae Bae Bae BAAM BAAM\n" +
                    "Bae Bae Bae BAAM BAAM", "2018.06.26"),
            Music("Weekend", "태연", R.drawable.img_album_exp6, "가장 가까운 바다\n" +
                    "혼자만의 영화관", "2021.07.06 싱글 댄스팝")
        )
        podcastList = mutableListOf(
            Music("제목", "가수", R.drawable.img_potcast_exp),
            Music("제목", "가수", R.drawable.img_potcast_exp),
            Music("제목", "가수", R.drawable.img_potcast_exp),
            Music("제목", "가수", R.drawable.img_potcast_exp)
        )
        videoCollectionList = mutableListOf(
            Music("제목", "가수", R.drawable.img_video_exp),
            Music("제목", "가수", R.drawable.img_video_exp),
            Music("제목", "가수", R.drawable.img_video_exp),
            Music("제목", "가수", R.drawable.img_video_exp)
        )
        homeViewPager1List = mutableListOf(
            R.drawable.img_home_viewpager_exp,
            R.drawable.img_home_viewpager_exp,
            R.drawable.img_home_viewpager_exp,
        )
        homeViewPager2List = mutableListOf(
            R.drawable.img_home_viewpager_exp2,
            R.drawable.img_home_viewpager_exp2,
            R.drawable.img_home_viewpager_exp2
        )
    }

    private fun initRecyclerView(view: View, recyclerViewId: Int, viewHolderType: Int){
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val recyclerView = view.findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager = layoutManager
        val recyclerViewItemList = when(recyclerViewId){
            R.id.home_new_music_daily_recyclerview -> musicList
            R.id.home_podcast_recyclerview -> podcastList
            R.id.home_video_collection_recyclerview -> videoCollectionList
            else -> emptyList()
        }.toMutableList()
        newMusicDailyAdapter = NewMusicDailyAdapter(recyclerViewItemList, viewHolderType, this)
        recyclerView.adapter = newMusicDailyAdapter
    }

    private fun initViewPager(){
        homeViewPagerAdapter = HomeViewPagerAdapter(homeViewPager1List, ADD)
        binding?.homeAdViewpager1?.adapter = homeViewPagerAdapter

        homeViewPagerAdapter = HomeViewPagerAdapter(homeViewPager2List, ADD)
        binding?.homeAdViewpager2?.adapter = homeViewPagerAdapter
    }

    private fun checkNewMusicDailyCategory(){
        binding?.homeNewMusicDailyDomesticTv?.setOnClickListener {
            binding?.homeNewMusicDailyDomesticTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_selected))
            binding?.homeNewMusicDailyForeignTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
            binding?.homeNewMusicDailySynthesisTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
        }
        binding?.homeNewMusicDailyForeignTv?.setOnClickListener {
            binding?.homeNewMusicDailyDomesticTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
            binding?.homeNewMusicDailyForeignTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_selected))
            binding?.homeNewMusicDailySynthesisTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
        }
        binding?.homeNewMusicDailySynthesisTv?.setOnClickListener {
            binding?.homeNewMusicDailyDomesticTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
            binding?.homeNewMusicDailyForeignTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_unselected))
            binding?.homeNewMusicDailySynthesisTv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.category_selected))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(music: Music, viewType: Int) {
        val bundle = Bundle()
        when(viewType) {
            PLAY_BTN -> {
                bundle.apply {
                    putString("music_title", music.title)
                    putString("music_singer", music.singer)
                    putInt("musicImageResId", music.musicImageResId ?: 0)
                    putString("lyrics", music.lyrics)
                }
                parentFragmentManager.setFragmentResult("music", bundle)
            }
            else -> {
                var tempFragment = AlbumFragment()
                bundle.putString("music_title", music.title)
                bundle.putString("music_singer", music.singer)
                bundle.putInt("musicImageResId", music.musicImageResId ?: 0)
                bundle.putString("albumInfo", music.albumInfo)
                tempFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment_container, tempFragment)
                    .addToBackStack("album_fragment")
                    .commitAllowingStateLoss()
            }
        }
    }
}
