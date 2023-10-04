package umc.mission.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import umc.mission.floclone.adapter.ViewpagerFragmentAdapter
import umc.mission.floclone.databinding.FragmentAlbumBinding

class AlbumFragment: Fragment() {
    private lateinit var binding: FragmentAlbumBinding
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
        binding.albumAlbumTitleTv.text = arguments?.getString("music_title")
        binding.albumAlbumSingerTv.text = arguments?.getString("music_singer")
        binding.albumAlbumDateTv.text = arguments?.getString("albumInfo")
        binding.albumAlbumImgIv.setImageResource(arguments?.getInt("musicImageResId") ?: 0)
    }

    private fun initViewPager2(){
        val fragmentStateAdapter = ViewpagerFragmentAdapter(requireActivity())
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