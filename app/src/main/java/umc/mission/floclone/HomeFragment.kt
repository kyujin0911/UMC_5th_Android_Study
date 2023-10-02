package umc.mission.floclone

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var newMusicDailyList: MutableList<NewMusicDaily>
    private lateinit var podcastList: MutableList<NewMusicDaily>
    private lateinit var videoCollectionList: MutableList<NewMusicDaily>
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
        newMusicDailyList = mutableListOf(
            NewMusicDaily("Next Level", "aespa", R.drawable.img_album_exp3),
            NewMusicDaily("작은 것들을 위한 시", "방탄소년단", R.drawable.img_album_exp4),
            NewMusicDaily("BAAM", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5),
            NewMusicDaily("Weekend", "태연", R.drawable.img_album_exp6)
        )
        podcastList = mutableListOf(
            NewMusicDaily("제목", "가수", R.drawable.img_potcast_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_potcast_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_potcast_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_potcast_exp)
        )
        videoCollectionList = mutableListOf(
            NewMusicDaily("제목", "가수", R.drawable.img_video_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_video_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_video_exp),
            NewMusicDaily("제목", "가수", R.drawable.img_video_exp)
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
            R.id.home_new_music_daily_recyclerview -> newMusicDailyList
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

    override fun onClick(newMusicDaily: NewMusicDaily, viewType: Int) {
        val bundle = Bundle()
        bundle.putString("music_title", newMusicDaily.title)
        bundle.putString("music_singer", newMusicDaily.singer)
        bundle.putInt("musicImageResId", newMusicDaily.musicImageResId)
        parentFragmentManager.setFragmentResult("music", bundle)
        when(viewType) {
            PLAY_BTN -> {
                parentFragmentManager.setFragmentResult("music", bundle)
            }
            else -> {
                var tempFragment = AlbumFragment()
                var bundle = Bundle()
                bundle.putString("music_title", newMusicDaily.title)
                bundle.putString("music_singer", newMusicDaily.singer)
                tempFragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fragment_container, tempFragment)
                    .commitAllowingStateLoss()
            }
        }

    }

}