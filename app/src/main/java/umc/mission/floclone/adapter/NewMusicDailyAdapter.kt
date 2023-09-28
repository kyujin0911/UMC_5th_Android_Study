package umc.mission.floclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.NewMusicDaily
import umc.mission.floclone.databinding.ItemHomeVideoCollectionBinding
import umc.mission.floclone.databinding.ItemNewMusicDailyBinding

class NewMusicDailyAdapter(
    private val newMusicDailyList: MutableList<NewMusicDaily>,
    private val viewHolderType: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class NewMusicDailyViewHolder(private val binding: ItemNewMusicDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newMusicDaily: NewMusicDaily) {
            binding.apply {
                tvRecyclerviewNewMusicDailyTitle.text = newMusicDaily.title
                tvRecyclerviewNewMusicDailySinger.text = newMusicDaily.singer
                ivRecyclerviewNewMusicDailyImg.setImageResource(newMusicDaily.musicImageResId)
            }
        }
    }

    class VideoCollectionViewHolder(private val binding: ItemHomeVideoCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newMusicDaily: NewMusicDaily) {
            binding.apply {
                tvRecyclerviewVideoCollectionTitle.text = newMusicDaily.title
                tvRecyclerviewVideoCollectionSinger.text = newMusicDaily.singer
                ivRecyclerviewVideoCollectionImg.setImageResource(newMusicDaily.musicImageResId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        return if (viewHolderType == NEW_MUSIC_DAILY) {
            val binding = ItemNewMusicDailyBinding.inflate(inflater, parent, false)
            NewMusicDailyViewHolder(binding)
        } else {
            val binding = ItemHomeVideoCollectionBinding.inflate(inflater, parent, false)
            VideoCollectionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newMusicDaily = newMusicDailyList[position]
        if(viewHolderType == NEW_MUSIC_DAILY){
            (holder as NewMusicDailyViewHolder).bind(newMusicDaily)
        }
        else{
            (holder as VideoCollectionViewHolder).bind(newMusicDaily)
        }
    }

    override fun getItemCount(): Int {
        return newMusicDailyList.size
    }

    companion object {
        const val NEW_MUSIC_DAILY = 0
        const val VIDEO_COLLECTION = 1
    }

}