package umc.mission.floclone.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import umc.mission.floclone.*

class ViewpagerFragmentAdapter(fragmentActivity: FragmentActivity, private val caller: Int, private val bundle: Bundle? = null): FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = if(caller==ALBUM)listOf(AlbumBSideTrackFragment(), DetailFragment(), VideoFragment())
    else listOf(DownloadedMusicFragment(), MusicFileFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        if(caller == ALBUM) {
            fragmentList[0].arguments = bundle
        }
        return fragmentList[position]
    }

    companion object{
        const val ALBUM = 0
        const val LOCKER = 1
    }
}