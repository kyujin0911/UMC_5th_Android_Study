package umc.mission.floclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.Music
import umc.mission.floclone.databinding.ItemBSideTrackBinding
import umc.mission.floclone.databinding.ItemHomeVideoCollectionBinding
import umc.mission.floclone.databinding.ItemNewMusicDailyBinding

class NewMusicDailyAdapter(
    private val musicList: MutableList<Music>,
    private val viewHolderType: Int,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class NewMusicDailyViewHolder(private val binding: ItemNewMusicDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val playBtn = binding.itemNewMusicDailyPlayBtn
        val musicImg = binding.ivRecyclerviewNewMusicDailyImg
        fun bind(music: Music) {
            binding.apply {
                tvRecyclerviewNewMusicDailyTitle.text = music.title
                tvRecyclerviewNewMusicDailySinger.text = music.singer
                ivRecyclerviewNewMusicDailyImg.setImageResource(music.musicImageResId ?: 0)
            }
        }
    }

    class VideoCollectionViewHolder(private val binding: ItemHomeVideoCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.apply {
                tvRecyclerviewVideoCollectionTitle.text = music.title
                tvRecyclerviewVideoCollectionSinger.text = music.singer
                ivRecyclerviewVideoCollectionImg.setImageResource(music.musicImageResId ?: 0)
            }
        }
    }

    class BSideTrackViewHolder(private val binding: ItemBSideTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.apply {
                itemTrackMusicTitleTv.text = music.title
                itemTrackSingerTv.text = music.singer
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return when (viewHolderType) {
            NEW_MUSIC_DAILY -> {
                val binding = ItemNewMusicDailyBinding.inflate(inflater, parent, false)
                NewMusicDailyViewHolder(binding)
            }
            VIDEO_COLLECTION -> {
                val binding = ItemHomeVideoCollectionBinding.inflate(inflater, parent, false)
                VideoCollectionViewHolder(binding)
            }
            else -> {
                val binding = ItemBSideTrackBinding.inflate(inflater, parent, false)
                BSideTrackViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newMusicDaily = musicList[position]
        when (viewHolderType) {
            NEW_MUSIC_DAILY -> {
                (holder as NewMusicDailyViewHolder).apply {
                    bind(newMusicDaily)
                    playBtn.setOnClickListener { itemClickListener?.onClick(newMusicDaily, PLAY_BTN) }
                    musicImg.setOnClickListener { itemClickListener?.onClick(newMusicDaily, MUSIC_IMG)
                    }
                }
            }
            VIDEO_COLLECTION -> {(holder as VideoCollectionViewHolder).bind(newMusicDaily)}
            else -> {(holder as BSideTrackViewHolder).bind(newMusicDaily)}
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    companion object {
        const val NEW_MUSIC_DAILY = 0
        const val VIDEO_COLLECTION = 1
        const val B_SIDE_TRACK = 2
        const val PLAY_BTN = 3
        const val MUSIC_IMG = 4
    }

    interface ItemClickListener {
        fun onClick(music: Music, viewType: Int)
    }
}