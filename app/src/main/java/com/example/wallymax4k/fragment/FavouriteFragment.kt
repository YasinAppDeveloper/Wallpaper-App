package com.example.wallymax4k.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.wallymax4k.adapter.ShowFavWallpaperFragmentAdapter
import com.example.wallymax4k.databinding.FragmentFavouriteBinding
import com.example.wallymax4k.room_database.WallpaperDatabase


class FavouriteFragment : Fragment() {
    private val binding by lazy {
        FragmentFavouriteBinding.inflate(layoutInflater)
    }
    private lateinit var favAdapter: ShowFavWallpaperFragmentAdapter
   // private var listOfFav = ArrayList<FavouriteWallpaper>()
    private lateinit var wallpaperDatabase: WallpaperDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

      //  listOfFav = arrayListOf()

        wallpaperDatabase = WallpaperDatabase.getInstance(requireContext())
        wallpaperDatabase.favouriteWallpaper().getAllFavWallpaper().observe(requireActivity(),
            Observer { list ->
                try {
                    binding.recyclerViewCategoryShowInFragment.layoutManager = GridLayoutManager(
                        requireContext(), 3,
                        VERTICAL, false
                    )
                    favAdapter = ShowFavWallpaperFragmentAdapter(requireContext(),list)
                    binding.recyclerViewCategoryShowInFragment.adapter = favAdapter

                } catch (e: Exception) {

                }

            })

        binding.backToMainFragmentFav.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }
}