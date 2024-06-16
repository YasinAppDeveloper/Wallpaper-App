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
import com.example.wallymax4k.activity.ShowFavActivity
import com.example.wallymax4k.room_database.FavouriteWallpaper
import com.example.wallymax4k.room_database.WallpaperDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("MemberVisibilityCanBePrivate", "SENSELESS_COMPARISON")
class ShowFavWallpaperFragmentAdapter(
    var context: Context,
    var listFavWallpaper: List<FavouriteWallpaper>,
) : RecyclerView.Adapter<ShowFavWallpaperFragmentAdapter.FavWallpaperHolder>() {

    inner class FavWallpaperHolder(val view: View) : RecyclerView.ViewHolder(view){
        val favImage = itemView.findViewById<ImageView>(R.id.favWallpaperImageItem)
        val favIcon = itemView.findViewById<ImageView>(R.id.favouriteIconFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavWallpaperHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fav_wallpaper_item,parent,false)
        return FavWallpaperHolder(view)
    }

    override fun getItemCount(): Int {
       return listFavWallpaper.size
    }

    override fun onBindViewHolder(holder: FavWallpaperHolder, position: Int) {
        Glide.with(context).load(listFavWallpaper[position].url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.favImage)

        // wallpaper click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShowFavActivity::class.java)
            intent.putParcelableArrayListExtra("list",ArrayList(listFavWallpaper))
            intent.putExtra("pos",position)
            context.startActivity(intent)
        }

        val database = WallpaperDatabase.getInstance(context)
        try {
            if (database.favouriteWallpaper().isExist(listFavWallpaper[position].id) != null) {
                holder.favIcon.setImageResource(R.drawable.wallpaper_favourite_icon)

            } else {
                holder.favIcon.setImageResource(R.drawable.fav)
            }
        }catch (e:Exception){}



        holder.favIcon.setOnClickListener {
            try {
                if (database.favouriteWallpaper().isExist(listFavWallpaper[position].id) != null) {
                    deleteToFavourite(
                        listFavWallpaper[position].id,
                        listFavWallpaper[position].url,
                        holder.favIcon,
                        database
                    )
                    holder.favIcon.setImageResource(R.drawable.fav)
                } else {
                    addToFavourite(
                        listFavWallpaper[position].id,
                        listFavWallpaper[position].url,
                        holder.favIcon,
                        database
                    )
                    holder.favIcon.setImageResource(R.drawable.wallpaper_favourite_icon)
                }
            } catch (e: Exception) {
            }


        }
    }
    private fun deleteToFavourite(
        id: String,
        wallpaper: String,
        favourite: ImageView,
        database: WallpaperDatabase
    ) {
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val wallpaperData = FavouriteWallpaper(id,wallpaper)
                database.favouriteWallpaper().deleteFav(wallpaperData)

            }
        }catch (e:Exception){}
    }
    private fun addToFavourite(
        id: String,
        wallpaper: String,
        favourite: ImageView,
        database: WallpaperDatabase
    ) {
        try {
            val wallpaperData = FavouriteWallpaper(id,wallpaper)
            CoroutineScope(Dispatchers.IO).launch {
                database.favouriteWallpaper().insert(wallpaperData)
            }
        }catch (e:Exception){}
    }
}