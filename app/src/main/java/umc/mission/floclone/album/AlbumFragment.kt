package umc.mission.floclone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import umc.mission.floclone.adapter.ViewpagerFragmentAdapter
import umc.mission.floclone.adapter.ViewpagerFragmentAdapter.Companion.ALBUM
import umc.mission.floclone.data.ALBUM_INFO
import umc.mission.floclone.data.MUSIC_IMG_RES_ID
import umc.mission.floclone.data.MUSIC_SINGER
import umc.mission.floclone.data.MUSIC_TITLE
import umc.mission.floclone.databinding.FragmentAlbumBinding

class AlbumFragment: Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var musicTitle: String
    private lateinit var musicSinger: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initViewPager2()
        binding.albumPreviousBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initView(){
        musicTitle = arguments?.getString(MUSIC_TITLE).toString()
        musicSinger = arguments?.getString(MUSIC_SINGER).toString()
        binding.albumAlbumTitleTv.text = musicTitle
        binding.albumAlbumSingerTv.text = musicSinger
        binding.albumAlbumDateTv.text = arguments?.getString(ALBUM_INFO)
        binding.albumAlbumImgIv.setImageResource(arguments?.getInt(MUSIC_IMG_RES_ID) ?: 0)
    }

    private fun initViewPager2(){
        var bundle = Bundle()
        bundle.putString(MUSIC_TITLE, musicTitle)
        bundle.putString(MUSIC_SINGER, musicSinger)
        val fragmentStateAdapter = ViewpagerFragmentAdapter(this, ALBUM, bundle)
        binding.albumViewpager2.adapter = fragmentStateAdapter
        TabLayoutMediator(binding.albumTablayout, binding.albumViewpager2) { tab, position ->
            binding.albumViewpager2.currentItem = tab.position
            tab.text = when(position){
                0 -> "수록곡"
                1 -> "상세정보"
                else -> "영상"
            }
        }.attach()
    }
}