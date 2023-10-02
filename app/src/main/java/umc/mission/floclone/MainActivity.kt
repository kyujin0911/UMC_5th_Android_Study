package umc.mission.floclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import umc.mission.floclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedMusicImgResId: Int? = null
    private val playerMusic = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result ->
        val musicTitle = result.data?.getStringExtra("music_title")
        val musicSinger = result.data?.getStringExtra("music_singer")
        if(musicTitle!=null && musicSinger != null && result.resultCode == RESULT_OK){
            binding.tvMainPlayingMusicTitle.text = musicTitle
            binding.tvMainPlayingMusicSinger.text = musicSinger
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        updateMusicPlayer()
        binding.activityMainPlayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("music_title", "${binding.tvMainPlayingMusicTitle.text}")
            intent.putExtra("music_singer", "${binding.tvMainPlayingMusicSinger.text}")
            intent.putExtra("lyrics", "I'm on the Next Level Yeah\n절대적 룰을 지켜")
            intent.putExtra("musicImageResId", selectedMusicImgResId)
            playerMusic.launch(intent)
        }
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_main_fragment_container, HomeFragment())
            commitAllowingStateLoss()
        }

        binding.activityMainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mnu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mnu_locker -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mnu_look -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.mnu_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun updateMusicPlayer(){
        binding.activityMainBtnPlay.setOnClickListener {
            binding.activityMainBtnPlay.isVisible = false
            binding.activityMainBtnPause.isVisible = true
        }

        binding.activityMainBtnPause.setOnClickListener {
            binding.activityMainBtnPlay.isVisible = true
            binding.activityMainBtnPause.isVisible = false
        }
        supportFragmentManager.setFragmentResultListener("music", this){
                _, bundle ->
            binding.tvMainPlayingMusicTitle.text = bundle.getString("music_title")
            binding.tvMainPlayingMusicSinger.text = bundle.getString("music_singer")
            selectedMusicImgResId = bundle.getInt("musicImageResId")
        }
    }
}