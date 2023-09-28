package umc.mission.floclone.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mission.floclone.databinding.ItemHomeViewPagerBinding

class HomeViewPagerAdapter(private val list: MutableList<Int>): RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    class HomeViewPagerViewHolder(private val binding: ItemHomeViewPagerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ResId: Int){
            binding.itemViewPagerIv.setImageResource(ResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewPagerViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemHomeViewPagerBinding.inflate(inflater, parent, false)
        return HomeViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewPagerViewHolder, position: Int) {
        val itemViewPager = list[position]
        holder.bind(itemViewPager)
    }
}