package com.example.wallymax4k.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallymax4k.R
import com.example.wallymax4k.activity.WallpaperShowActivity
import com.example.wallymax4k.model.WallpaperModel


class DisplayCategoryWallpaperAdapter(val context: Context, private val displayWallpaper:List<WallpaperModel>) : RecyclerView.Adapter<DisplayCategoryWallpaperAdapter.DisplayViewHolder>() {

    inner class DisplayViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val imageOfDisplayCategory = itemView.findViewById<ImageView>(R.id.wallpaperImageItemDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.display_wallpaper_item,parent,false)
        return DisplayViewHolder(view)
    }

    override fun getItemCount(): Int {
      return displayWallpaper.size
    }

    override fun onBindViewHolder(holder: DisplayViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(displayWallpaper[position].wallpaper)
            .into(holder.imageOfDisplayCategory)

        Glide.with(holder.itemView)
            .load(displayWallpaper[position].wallpaper)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageOfDisplayCategory)

        // wallpaper click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperShowActivity::class.java)
            intent.putParcelableArrayListExtra("list",ArrayList(displayWallpaper))
            intent.putExtra("pos",position)
            context.startActivity(intent)
        }

//        val database = WallpaperDatabase.getInstance(context).favouriteWallpaper()
//        try {
//            if (database.isExist(displayWallpaper[position].id) != null){
//                holder.displayWallpaperIcon.setImageResource(R.drawable.wallpaper_favourite_icon)
//
//            } else {
//                holder.displayWallpaperIcon.setImageResource(R.drawable.fav)
//
//            }
//        }catch (e:Exception){}

    }

}