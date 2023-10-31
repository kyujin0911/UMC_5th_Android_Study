package umc.mission.floclone

import android.content.*
import android.content.Context.MODE_PRIVATE
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import umc.mission.floclone.data.*
import umc.mission.floclone.databinding.ActivityMainBinding
import umc.mission.floclone.locker.LockerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedMusic: Music
    private var gson = Gson()
    private lateinit var sharedPreferences: SharedPreferences
    private val playerMusic = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val musicTitle = result.data?.getStringExtra(MUSIC_TITLE)
        val musicSinger = result.data?.getStringExtra(MUSIC_SINGER)
        if (musicTitle != null && musicSinger != null && result.resultCode == RESULT_OK) {
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
        initSelectedMusic()
        updateMusicPlayer()
        sharedPreferences = getSharedPreferences(MUSIC, MODE_PRIVATE)
        //registerReceiver(receiver, IntentFilter(SERVICE_TO_MAIN_INTENT_FILTER))

        binding.activityMainPlayer.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra(MUSIC_TITLE, selectedMusic.title)
            intent.putExtra(MUSIC_SINGER, selectedMusic.singer)
            intent.putExtra(LYRICS, selectedMusic.lyrics)
            intent.putExtra(MUSIC_IMG_RES_ID, selectedMusic.musicImageResId)
            intent.putExtra(SECOND, selectedMusic.second)
            intent.putExtra(PLAY_TIME, selectedMusic.playTime)
            intent.putExtra(IS_PLAYING, true)
            intent.putExtra(MUSIC_FILE_NAME, selectedMusic.musicFileName)
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

    private fun updateMusicPlayer() {
        binding.activityMainBtnPlay.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.activityMainBtnPause.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    private fun initSelectedMusic() {
        supportFragmentManager.setFragmentResultListener(MUSIC, this) { _, bundle ->
            selectedMusic = Music(
                bundle.getString(MUSIC_TITLE),
                bundle.getString(MUSIC_SINGER),
                bundle.getInt(MUSIC_IMG_RES_ID),
                bundle.getString(LYRICS),
                null,
                bundle.getInt(SECOND),
                bundle.getInt(PLAY_TIME),
                true,
                bundle.getString(MUSIC_FILE_NAME)
            )
            Log.d("smusic", "$selectedMusic")

            binding.tvMainPlayingMusicTitle.text = selectedMusic.title
            binding.tvMainPlayingMusicSinger.text = selectedMusic.singer
            //binding.activityMainMusicSeekbar.max = selectedMusic.playTime
            setPlayerStatus(selectedMusic.isPlaying)
        }
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        selectedMusic.isPlaying = isPlaying
        if (isPlaying) {
            binding.activityMainBtnPlay.visibility = View.GONE
            binding.activityMainBtnPause.visibility = View.VISIBLE
        } else {
            binding.activityMainBtnPlay.visibility = View.VISIBLE
            binding.activityMainBtnPause.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences(MUSIC, MODE_PRIVATE)
        val musicJson = sharedPreferences.getString(MUSIC_FILE_NAME, null)
        Log.d("mus", "$musicJson")
        selectedMusic = if (musicJson == null) {
            Music(
                "Next Level", "aespa", R.drawable.img_album_exp3, "I'm on the Next Level Yeah\n" +
                        "절대적 룰을 지켜", "2021.05.17 싱글 댄스팝", 0, 222, false, "music_nextlevel")
        } else {
            gson.fromJson(musicJson, Music::class.java)
        }
        binding.activityMainMusicSeekbar.progress = (selectedMusic.second*100000)/selectedMusic.playTime
    }
}