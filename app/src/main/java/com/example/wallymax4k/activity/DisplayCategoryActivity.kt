package com.example.wallymax4k.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.util.StatusBarHelper
import com.example.wallymax4k.adapter.DisplayCategoryWallpaperAdapter
import com.example.wallymax4k.databinding.ActivityDisplayCategoryBinding
import com.example.wallymax4k.model.WallpaperModel
import com.google.firebase.firestore.FirebaseFirestore

class DisplayCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayCategoryBinding
    private var id: String? = null
    private var name: String? = null
    private lateinit var displayCateList:MutableList<WallpaperModel>
    private lateinit var displayCategoryWallpaperAdapter: DisplayCategoryWallpaperAdapter
    private lateinit var db: FirebaseFirestore
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayCateList = mutableListOf()

        binding.backToHomeActivity.setOnClickListener {
            onBackPressed()
        }

        StatusBarHelper.setStatusBarColor(this,Color.WHITE)
        StatusBarHelper.setStatusBarLightMode(this,true)

        db = FirebaseFirestore.getInstance()

        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")

        binding.displayCategoryWallpaperRecyclerView.layoutManager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
        displayCategoryWallpaperAdapter = DisplayCategoryWallpaperAdapter(this,displayCateList)
        binding.displayCategoryWallpaperRecyclerView.adapter = displayCategoryWallpaperAdapter

        binding.displayCategoryNameOfTollBar.text = name
        try {
            db.collection("Category")
                .document(id!!).collection(id!!).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            for (doc in document.documents) {
                                val cateRes = doc.toObject(WallpaperModel::class.java)
                                displayCateList.add(cateRes!!)
                                binding.displayCategoryWallpaperRecyclerView.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                            }
                            displayCategoryWallpaperAdapter.notifyDataSetChanged()
                        }
                    }
                }
        }catch (e:Exception){}



    }
}