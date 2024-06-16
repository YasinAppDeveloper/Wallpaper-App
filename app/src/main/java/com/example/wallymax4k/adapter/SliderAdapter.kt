package com.example.imageslider

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

class SliderAdapter(val context: Context,private val imageUrls: List<WallpaperModel>) : RecyclerView.Adapter<SliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.slideWallpaperImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slide_wallpaper_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(imageUrls[position].wallpaper)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        // wallpaper click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WallpaperShowActivity::class.java)
            intent.putParcelableArrayListExtra("list",ArrayList(imageUrls))
            intent.putExtra("pos",position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}
