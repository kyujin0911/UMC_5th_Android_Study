package umc.mission.floclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import umc.mission.floclone.data.*
import umc.mission.floclone.databinding.ActivityMainBinding
import umc.mission.floclone.locker.LockerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedMusic: Music
    private val playerMusic = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result ->
        val musicTitle = result.data?.getStringExtra(MUSIC_TITLE)
        val musicSinger = result.data?.getStringExtra(MUSIC_SINGER)
        if(musicTitle!=null && musicSinger != null && result.resultCode == RESULT_OK){
            binding.tvMainPlayingMusicTitle.text = musicTitle
            binding.tvMainPlayingMusicSinger.text = musicSinger
            Toast.makeText(this, "앨범 제목: $musicTitle", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FloClone)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        updateMusicPlayer()
        binding.activityMainPlayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra(MUSIC_TITLE, selectedMusic.title)
            intent.putExtra(MUSIC_SINGER, selectedMusic.singer)
            intent.putExtra(LYRICS, selectedMusic.lyrics)
            intent.putExtra(MUSIC_IMG_RES_ID, selectedMusic.musicImageResId)
            intent.putExtra(SECOND, selectedMusic.second)
            intent.putExtra(PLAY_TIME, selectedMusic.playTime)
            intent.putExtra(IS_PLAYING, selectedMusic.isPlaying)
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
        supportFragmentManager.setFragmentResultListener(MUSIC, this){
                _, bundle ->
            selectedMusic = Music(bundle.getString(MUSIC_TITLE), bundle.getString(MUSIC_SINGER),
                bundle.getInt(MUSIC_IMG_RES_ID), bundle.getString(LYRICS), null, bundle.getInt(SECOND),
                bundle.getInt(PLAY_TIME), true)
            binding.tvMainPlayingMusicTitle.text = selectedMusic.title
            binding.tvMainPlayingMusicSinger.text = selectedMusic.singer
            binding.activityMainBtnPlay.isVisible = false
            binding.activityMainBtnPause.isVisible = true
        }
    }
}