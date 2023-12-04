package umc.mission.floclone.ui.main.locker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import umc.mission.floclone.R
import umc.mission.floclone.ui.adapter.SavedAlbumRVA
import umc.mission.floclone.data.entities.Album
import umc.mission.floclone.data.local.AUTH
import umc.mission.floclone.data.local.JWT
import umc.mission.floclone.data.local.SongDatabase
import umc.mission.floclone.databinding.BottomSheetLayoutBinding
import umc.mission.floclone.databinding.FragmentDownloadedMusicBinding

class SavedAlbumFragment: Fragment() {
    private lateinit var binding: FragmentDownloadedMusicBinding
    private lateinit var albumList: List<Album>
    private lateinit var savedAlbumRVA: SavedAlbumRVA
    private lateinit var bottomSheetLayoutBinding: BottomSheetLayoutBinding
    private lateinit var songDB: SongDatabase
    private lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadedMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        songDB = SongDatabase.getInstance(requireContext())!!
        initBottomSheetDialog()
        addSongs()
        initRV()
        binding.fragmentDownloadedMusicSelectAllTv.setOnClickListener {
            selectAll(true)
        }
    }

    private fun initRV() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.fragmentDownloadedMusicRV
        recyclerView.layoutManager = layoutManager
        savedAlbumRVA = SavedAlbumRVA()
        recyclerView.adapter = savedAlbumRVA
        savedAlbumRVA.submitList(albumList)
    }

    private fun addSongs() {
        val spf = activity?.getSharedPreferences(AUTH, AppCompatActivity.MODE_PRIVATE)
        val userId = spf!!.getString(JWT, null)!!
        albumList = songDB.albumDao().getLikedAlbums(userId)
    }
    private fun initBottomSheetDialog() {
        bottomSheetLayoutBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetLayoutBinding.root)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.setOnCancelListener {
            selectAll(false)
        }
    }

    private fun selectAll(toggle: Boolean) {
        if (toggle) {
            binding.fragmentDownloadedMusicSelectAllTv.text = "선택해제"
            binding.fragmentDownloadedMusicSelectAllTv.setTextColor(
                resources.getColor(R.color.category_selected)
            )
            binding.fragmentDownloadedMusicSelectAllBtn.setImageResource(
                R.drawable.btn_playlist_select_on
            )
            bottomSheetDialog.show()
        } else {
            binding.fragmentDownloadedMusicSelectAllTv.text = "전체선택"
            binding.fragmentDownloadedMusicSelectAllTv.setTextColor(Color.BLACK)
            binding.fragmentDownloadedMusicSelectAllBtn.setImageResource(R.drawable.btn_playlist_select_off)
        }
    }
}