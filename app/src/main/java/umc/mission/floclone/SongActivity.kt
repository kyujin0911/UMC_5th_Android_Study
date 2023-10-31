package umc.mission.floclone

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import umc.mission.floclone.data.*
import umc.mission.floclone.databinding.ActivitySongBinding
import java.util.*
import kotlin.concurrent.timer

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var music: Music
    private var mediaPlayer: MediaPlayer? = null
    private var timer: Timer? = null
    private var repeatBtnState = false
    private var randomBtnState = false
    private var likeBtnState = false
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMusic()
        initView()
        startMusic()
        pauseMusic()
        sendData()
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

        timer = Timer(music.playTime, music.isPlaying)
        timer?.start()
    }

    private fun initView() {
        binding.activitySongMusicTitleTv.text = music.title
        binding.activitySongSingerTv.text = music.singer
        binding.activitySongMusicLyricsTv.text = music.lyrics
        binding.activitySongAlbumImgIv.setImageResource(intent.getIntExtra(MUSIC_IMG_RES_ID, 0))
        binding.activitySongMusicPlayingTimeStartTv.text =
            String.format("%02d:%02d", music.second / 60, music.second % 60)
        binding.activitySongMusicPlayingTimeEndTv.text =
            String.format("%02d:%02d", music.playTime / 60, music.playTime % 60)
        binding.activitySongMusicSeekbar.progress = (music.second*1000/music.playTime)
        val musicFile = resources.getIdentifier(music.musicFileName, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, musicFile).apply {
            isLooping = repeatBtnState
        }
        setPlayerStatus(music.isPlaying)
    }

    private fun sendData() {
        binding.activitySongDownBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MUSIC_TITLE, music.title)
                putExtra(MUSIC_SINGER, music.singer)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateView() {
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
        music.second = ((binding.activitySongMusicSeekbar.progress * music.playTime) / 100) / 1000
        val sharedPreferences = getSharedPreferences(MUSIC, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val musicJson = gson.toJson(music)
        Log.d("music", "$musicJson")
        editor.putString(MUSIC_FILE_NAME, musicJson).apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        music.isPlaying = isPlaying
        timer?.isPlaying = isPlaying
        if (isPlaying) {
            binding.activitySongPlayerPlayBtn.visibility = View.GONE
            binding.activitySongPlayerPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.activitySongPlayerPlayBtn.visibility = View.VISIBLE
            binding.activitySongPlayerPauseBtn.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    inner class Timer(
        private val playTime: Int,
        var isPlaying: Boolean = true
    ) : Thread() {
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.activitySongMusicSeekbar.progress = ((mills / playTime) * 100).toInt()
                        }

                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.activitySongMusicPlayingTimeStartTv.text =
                                    String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                runOnUiThread {
                    Toast.makeText(this@SongActivity, "음악 재생 스레드 종료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}