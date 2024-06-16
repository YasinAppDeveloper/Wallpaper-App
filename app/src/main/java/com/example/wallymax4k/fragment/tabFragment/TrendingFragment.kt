package com.example.wallymax4k.fragment.tabFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallymax4k.R
import com.example.wallymax4k.adapter.TrendingAdapter
import com.example.wallymax4k.databinding.FragmentTrendingBinding
import com.example.wallymax4k.model.WallpaperModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Collections

class TrendingFragment : Fragment() {
   private val binding by lazy {
       FragmentTrendingBinding.inflate(layoutInflater)
   }
   private lateinit var trendingAdapter: TrendingAdapter
   private lateinit var wallpaperList :ArrayList<WallpaperModel>
   private lateinit var db:FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        wallpaperList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.trendingProgressBar.visibility = View.VISIBLE

        fetchWallpaperInDB()
        trendingAdapter = TrendingAdapter(requireContext(),wallpaperList)
        binding.trendingRecyclerview.adapter = trendingAdapter

        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    fun fetchWallpaperInDB(){
       db.collection("Trending")
           .get().addOnCompleteListener {
               if (it.isSuccessful){
                   val document  = it.result
                   if (document != null){
                       for (doc in document.documents){
                           val trendingData = doc.toObject(WallpaperModel::class.java)
                           wallpaperList.add(trendingData!!)
                           binding.trendingProgressBar.visibility = View.GONE
                       }
                       Collections.shuffle(wallpaperList)
                       trendingAdapter.notifyDataSetChanged()
                   }
               }
           }
    }
}