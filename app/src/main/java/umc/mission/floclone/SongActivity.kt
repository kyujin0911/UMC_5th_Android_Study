package umc.mission.floclone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mission.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var musicTitle: String
    private lateinit var musicSinger: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        binding.activitySongDownBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("music_title", musicTitle)
                putExtra("music_singer", musicSinger)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private fun initView(){
        val musicTitle = intent.getStringExtra("music_title")
        val musicSinger = intent.getStringExtra("music_singer")
        val musicLyrics = intent.getStringExtra("lyrics")
        binding.activitySongMusicTitleTv.text = musicTitle
        binding.activitySongSingerTv.text = musicSinger
        binding.activitySongMusicLyricsTv.text = musicLyrics
        binding.activitySongAlbumImgIv.setImageResource(intent.getIntExtra("musicImageResId", 0))
    }
}