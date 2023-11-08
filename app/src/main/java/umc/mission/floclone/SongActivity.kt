package umc.mission.floclone

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.mission.floclone.data.*
import umc.mission.floclone.databinding.ActivitySongBinding
import java.text.SimpleDateFormat

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var music: Music
    private var mediaPlayer: MediaPlayer? = null
    private var repeatBtnState = false
    private var gson = Gson()
    private var timeFormat = SimpleDateFormat("mm:ss")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMusic()
        initView()
        startMusic()
        pauseMusic()
        returnMainActivity()
        updateView()
    }

    private fun initMusic() {
        music = Music(
            intent.getStringExtra(MUSIC_TITLE), intent.getStringExtra(MUSIC_SINGER),
            intent.getIntExtra(MUSIC_IMG_RES_ID, 0), intent.getStringExtra(LYRICS), null,
            intent.getIntExtra(SECOND, 0), intent.getIntExtra(PLAY_TIME, 0), intent.getBooleanExtra(
                IS_PLAYING, true
            ), intent.getStringExtra(MUSIC_FILE_NAME)
        )
    }

    private fun initView() {
        binding.activitySongMusicTitleTv.text = music.title
        binding.activitySongSingerTv.text = music.singer
        binding.activitySongMusicLyricsTv.text = music.lyrics
        binding.activitySongAlbumImgIv.setImageResource(intent.getIntExtra(MUSIC_IMG_RES_ID, 0))

        val musicFile = resources.getIdentifier(music.musicFileName, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, musicFile).apply {
            isLooping = repeatBtnState
        }

        binding.activitySongMusicPlayingTimeEndTv.text =
            timeFormat.format(mediaPlayer?.duration)
        binding.activitySongMusicPlayingTimeStartTv.text =
            timeFormat.format(music.second)

        binding.activitySongMusicSeekbar.max = mediaPlayer?.duration!!
        binding.activitySongMusicSeekbar.progress = music.second
        mediaPlayer?.seekTo(music.second)
        Log.d("duration", "${mediaPlayer?.duration}")
        setPlayerStatus(music.isPlaying)
    }

    private fun returnMainActivity() {
        binding.activitySongDownBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MUSIC_TITLE, music.title)
                putExtra(MUSIC_SINGER, music.singer)
                putExtra(CURRENT_POSITION, mediaPlayer?.currentPosition)
                putExtra(IS_PLAYING, mediaPlayer?.isPlaying)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateView() {
        var randomBtnState = false
        var likeBtnState = false
        binding.activitySongPlayerRepeatBtn.setOnClickListener {
            repeatBtnState = !repeatBtnState
            binding.activitySongPlayerRepeatBtn.setImageResource(
                if (repeatBtnState) R.drawable.selected_repeat else R.drawable.nugu_btn_repeat_inactive
            )
        }
        binding.activitySongPlayerRandomBtn.setOnClickListener {
            randomBtnState = !randomBtnState
            binding.activitySongPlayerRandomBtn.setImageResource(
                if (randomBtnState) R.drawable.selected_random else R.drawable.nugu_btn_random_inactive
            )
        }

        binding.activitySongLikeBtn.setOnClickListener {
            likeBtnState = !likeBtnState
            binding.activitySongLikeBtn.setImageResource(if (likeBtnState) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off)
        }
    }

    private fun startMusic() {
        binding.activitySongPlayerPlayBtn.setOnClickListener {
            setPlayerStatus(true)
        }
    }

    private fun pauseMusic() {
        binding.activitySongPlayerPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        music.second = mediaPlayer?.currentPosition!!
            //((binding.activitySongMusicSeekbar.progress * music.playTime) / 100) / 1000
        val sharedPreferences = getSharedPreferences(MUSIC, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val musicJson = gson.toJson(music)
        editor.putString(MUSIC_FILE_NAME, musicJson).apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        music.isPlaying = isPlaying
        if (isPlaying) {
            binding.activitySongPlayerPlayBtn.visibility = View.GONE
            binding.activitySongPlayerPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.activitySongPlayerPlayBtn.visibility = View.VISIBLE
            binding.activitySongPlayerPauseBtn.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
        updateSeekBar()
    }

    private fun updateSeekBar() {
        CoroutineScope(Dispatchers.Main).launch {
            while(mediaPlayer?.isPlaying!!) {
                delay(50)
                binding.activitySongMusicSeekbar.progress = mediaPlayer?.currentPosition!!
                binding.activitySongMusicPlayingTimeStartTv.text =
                    timeFormat.format(mediaPlayer?.currentPosition)
            }
        }
    }
}