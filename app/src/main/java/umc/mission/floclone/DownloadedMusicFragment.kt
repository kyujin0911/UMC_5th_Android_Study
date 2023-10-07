package umc.mission.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mission.floclone.databinding.FragmentDownloadedMusicBinding

class DownloadedMusicFragment: Fragment() {
    private lateinit var binding: FragmentDownloadedMusicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadedMusicBinding.inflate(inflater, container, false)
        return binding.root
    }
}