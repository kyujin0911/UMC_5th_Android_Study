package umc.mission.floclone.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.mission.floclone.AlbumBSideTrackFragment
import umc.mission.floclone.Music

class ViewpagerFragmentAdapter(fragmentActivity: FragmentActivity, private val bundle: Bundle): FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = listOf<Fragment>(AlbumBSideTrackFragment(), AlbumBSideTrackFragment(), AlbumBSideTrackFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        fragmentList[0].arguments = bundle
        return fragmentList[position]
    }
}