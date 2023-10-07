package umc.mission.floclone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import umc.mission.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var musicTitle: String
    private lateinit var musicSinger: String
    private lateinit var musicLyrics: String
    private var repeatBtnState = false
    private var randomBtnState = false
    private var likeBtnState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        sendData()
        updateView()
    }

    private fun initView() {
        musicTitle = intent.getStringExtra("music_title").toString()
        musicSinger = intent.getStringExtra("music_singer").toString()
        musicLyrics = intent.getStringExtra("lyrics").toString()
        binding.activitySongMusicTitleTv.text = musicTitle
        binding.activitySongSingerTv.text = musicSinger
        binding.activitySongMusicLyricsTv.text = musicLyrics
        binding.activitySongAlbumImgIv.setImageResource(intent.getIntExtra("musicImageResId", 0))
    }

    private fun sendData() {
        binding.activitySongDownBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("music_title", musicTitle)
                putExtra("music_singer", musicSinger)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateView() {
        binding.activitySongPlayerRepeatBtn.setOnClickListener {
            repeatBtnState = !repeatBtnState
            binding.activitySongPlayerRepeatBtn.setImageResource(
                if(repeatBtnState)R.drawable.selected_repeat else R.drawable.nugu_btn_repeat_inactive)
        }
        binding.activitySongPlayerRandomBtn.setOnClickListener {
            randomBtnState = !randomBtnState
            binding.activitySongPlayerRandomBtn.setImageResource(
                if(randomBtnState)R.drawable.selected_random else R.drawable.nugu_btn_random_inactive)
        }

        binding.activitySongLikeBtn.setOnClickListener {
            likeBtnState = !likeBtnState
            binding.activitySongLikeBtn.setImageResource(if(likeBtnState) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off)
        }

        binding.activitySongPlayerPlayBtn.setOnClickListener {
            binding.activitySongPlayerPlayBtn.visibility = View.GONE
            binding.activitySongPlayerPauseBtn.visibility = View.VISIBLE
        }

        binding.activitySongPlayerPauseBtn.setOnClickListener {
            binding.activitySongPlayerPlayBtn.visibility = View.VISIBLE
            binding.activitySongPlayerPauseBtn.visibility = View.GONE
        }
    }
}