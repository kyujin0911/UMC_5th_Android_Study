package umc.mission.floclone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.mission.floclone.AlbumBSideTrackFragment

class ViewpagerFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = listOf<Fragment>(AlbumBSideTrackFragment(), AlbumBSideTrackFragment(), AlbumBSideTrackFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}