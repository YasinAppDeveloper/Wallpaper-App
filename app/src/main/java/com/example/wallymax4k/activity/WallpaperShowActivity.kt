package com.example.wallymax4k.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.instagram.util.StatusBarHelper
import com.example.wallymax4k.R
import com.example.wallymax4k.adapter.ShowWallpaperAdapter
import com.example.wallymax4k.databinding.ActivityWallpaperShowBinding
import com.example.wallymax4k.model.WallpaperModel

class WallpaperShowActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWallpaperShowBinding.inflate(layoutInflater)
    }
    private lateinit var viewPager2: ViewPager2
    private lateinit var showWallpaperAdapter: ShowWallpaperAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarHelper.setStatusBarColor(this,Color.WHITE)
        StatusBarHelper.setStatusBarLightMode(this,true)

        viewPager2 = findViewById(R.id.showWallpaperViewpager)

        val position = intent.getIntExtra("pos",0)
         val listOfTrending = intent.getParcelableArrayListExtra<WallpaperModel>("list")

        showWallpaperAdapter = listOfTrending?.let { ShowWallpaperAdapter(this@WallpaperShowActivity,it) }!!
       viewPager2.adapter = showWallpaperAdapter
       viewPager2.setCurrentItem(position,false)

    }
}