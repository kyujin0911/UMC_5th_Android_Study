package umc.mission.floclone.ui.main.look

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import umc.mission.floclone.ui.adapter.FloChartRVA
import umc.mission.floclone.databinding.FragmentLookBinding
import umc.mission.floclone.data.remote.song.FloChartResult
import umc.mission.floclone.data.remote.song.SongService

class LookFragment : Fragment(), LookView {
    private lateinit var binding: FragmentLookBinding
    private lateinit var floChartAdapter: FloChartRVA

    override fun onStart() {
        super.onStart()
        getSongs()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecyclerView(result: FloChartResult){
        floChartAdapter = FloChartRVA(requireActivity(), result)

        binding.lookFloChartRv.adapter = floChartAdapter
    }

    private fun getSongs(){
        val songService = SongService()
        songService.setLookView(this)

        songService.getSongs()
    }

    override fun onGetSongLoading() {
        binding.lookLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetSongSuccess(code: Int, result: FloChartResult) {
        binding.lookLoadingPb.visibility = View.GONE
        initRecyclerView(result)
    }

    override fun onGetSongFailure(code: Int, message: String) {
        binding.lookLoadingPb.visibility = View.GONE
        Log.d("LOOK_FRAG/SONG-RESPONSE", message)
    }
}