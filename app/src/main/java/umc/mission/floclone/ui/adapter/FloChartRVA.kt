package umc.mission.floclone.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mission.floclone.databinding.ItemLookChartBinding
import umc.mission.floclone.data.remote.song.FloChartResult


class FloChartRVA(val context: Context, val result : FloChartResult) : RecyclerView.Adapter<FloChartRVA.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLookChartBinding = ItemLookChartBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind(result.songs[position])

        if(result.songs[position].coverImgUrl == "" || result.songs[position].coverImgUrl == null){

        } else {
            Log.d("image",result.songs[position].coverImgUrl )
            Glide.with(context).load(result.songs[position].coverImgUrl).placeholder(null).into(holder.coverImg)
        }
        holder.title.text = result.songs[position].title
        holder.singer.text = result.songs[position].singer

    }

    override fun getItemCount(): Int = result.songs.size


    inner class ViewHolder(val binding: ItemLookChartBinding) : RecyclerView.ViewHolder(binding.root){

        val coverImg : ImageView = binding.itemSongImgIv
        val title : TextView = binding.itemSongTitleTv
        val singer : TextView = binding.itemSongSingerTv

//        fun bind(song: FloChartSongs){
//            if(song.coverImgUrl == "" || song.coverImgUrl == null) {
//            } else {
//                Glide.with(context).load(song.coverImgUrl).into(binding.itemSongImgIv)
//            }
//
//            binding.itemSongTitleTv.text = song.title
//            binding.itemSongSingerTv.text = song.singer
//        }
    }

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
}