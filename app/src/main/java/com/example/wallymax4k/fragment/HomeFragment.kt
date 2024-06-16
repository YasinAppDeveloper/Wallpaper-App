package com.example.wallymax4k.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageslider.SliderAdapter
import com.example.wallymax4k.adapter.CategoryAdapter
import com.example.wallymax4k.adapter.TabPagerAdapter
import com.example.wallymax4k.databinding.FragmentHomeBinding
import com.example.wallymax4k.model.CategoryModel
import com.example.wallymax4k.model.WallpaperModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Collections


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listOfSlideWallpaper: ArrayList<WallpaperModel>
    private lateinit var db: FirebaseFirestore
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList:ArrayList<CategoryModel>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        db = FirebaseFirestore.getInstance()
        listOfSlideWallpaper = arrayListOf()
        categoryList = arrayListOf()


        setTabLayoutCode()

        fetchSliderData()
        fetchDbCategory()
        setCategoryRecyclerView()


        binding.moveToCateFragment.setOnClickListener {

        }


        binding.categoryProgressBar.visibility = View.VISIBLE
      //  binding.sliderProgressBar.visibility = View.VISIBLE

        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    private fun fetchDbCategory() {
        db.collection("Category").limit(7)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                 val document = task.result
                    if (document != null){
                        for (doc in document.documents){
                            val categoryData = doc.toObject(CategoryModel::class.java)
                            categoryList.add(categoryData!!)
                            binding.categoryProgressBar.visibility = View.GONE
                        }
                        Collections.shuffle(categoryList)
                        categoryAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    private fun setCategoryRecyclerView(){
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        categoryAdapter = CategoryAdapter(requireContext(),categoryList)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun fetchSliderData(){
        db.collection("SliderWallpaper")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val documents = it.result
                    if (documents != null) {
                        for (doc in documents.documents) {
                            val listOfBestOfMonth = doc.toObject(WallpaperModel::class.java)
                            binding.bestOfTheDayProgressBar.visibility = View.GONE
                            listOfSlideWallpaper.add(listOfBestOfMonth!!)
                        }

                        val adapter = SliderAdapter(requireContext(),listOfSlideWallpaper)
                        Collections.shuffle(listOfSlideWallpaper)
                        binding.viewPager.adapter = adapter
                    }
                }
            }
    }
    private fun setTabLayoutCode() {
      binding.viewPager2Tab.adapter = TabPagerAdapter(this)
      TabLayoutMediator(binding.tabLayout,binding.viewPager2Tab){tab,pos->
          when(pos){
              0 ->{
                  tab.text = "Trending"
                //  tab.setIcon(com.example.wallymax4k.R.drawable.trending)
              }
              1 -> {
                  tab.text = "Recent"
                //  tab.setIcon(com.example.wallymax4k.R.drawable.recent)
              }
              2 -> {
                  tab.text = "HD Plus"
              }
              3 ->{
                  tab.text = "New"
                 // tab.setIcon(com.example.wallymax4k.R.drawable.new_icon)
              }
          }
      }.attach()

    }
}

