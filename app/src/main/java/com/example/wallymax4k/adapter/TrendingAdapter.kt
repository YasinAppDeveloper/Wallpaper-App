package com.example.wallymax4k.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallymax4k.R
import com.example.wallymax4k.activity.WallpaperShowActivity
import com.example.wallymax4k.databinding.WallpaperItemBinding
import com.example.wallymax4k.model.WallpaperModel
import com.example.wallymax4k.room_database.WallpaperDatabase

@Suppress("SENSELESS_COMPARISON")
class TrendingAdapter(val context: Context, val listOfTrendingWallpaper:ArrayList<WallpaperModel>) : RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    inner class TrendingViewHolder(var binding:WallpaperItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
     return TrendingViewHolder(WallpaperItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
       return listOfTrendingWallpaper.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
       Glide.with(context)
           .load(listOfTrendingWallpaper[position].wallpaper)
           .transition(DrawableTransitionOptions.withCrossFade())
           .into(holder.binding.wallpaperImageItem)


        val database = WallpaperDatabase.getInstance(context).favouriteWallpaper()
        try {
            if (database.isExist(listOfTrendingWallpaper[position].id) != null){
                holder.binding.favouriteIcon.setImageResource(R.drawable.wallpaper_favourite_icon)

            } else {
                holder.binding.favouriteIcon.setImageResource(R.drawable.fav)

            }
        }catch (e:Exception){}


        // wallpaper click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperShowActivity::class.java)
            intent.putParcelableArrayListExtra("list",listOfTrendingWallpaper)
            intent.putExtra("pos",position)
            context.startActivity(intent)
        }
    }
}