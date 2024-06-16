package com.example.wallymax4k.activity

import FavouriteAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.wallymax4k.R
import com.example.wallymax4k.room_database.FavouriteWallpaper

class ShowFavActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var showWallpaperAdapter: FavouriteAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_fav)

        val position = intent.getIntExtra("pos",0)
        val listOfTrending = intent.getParcelableArrayListExtra<FavouriteWallpaper>("list")

        viewPager2 = findViewById(R.id.showWallpaperViewpagerFav)

        showWallpaperAdapter = listOfTrending?.let { FavouriteAdapter(this@ShowFavActivity,it) }!!
        viewPager2.adapter = showWallpaperAdapter
        viewPager2.setCurrentItem(position,false)

      //  Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show()

    }
}