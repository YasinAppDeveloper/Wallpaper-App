package com.example.wallymax4k.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallymax4k.fragment.tabFragment.Fragment_4K
import com.example.wallymax4k.fragment.tabFragment.NewFragment
import com.example.wallymax4k.fragment.tabFragment.RecentFragment
import com.example.wallymax4k.fragment.tabFragment.TrendingFragment

class TabPagerAdapter(fragment: Fragment)  : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0 -> TrendingFragment()
            1 -> RecentFragment()
            2 -> Fragment_4K()
            else -> NewFragment()
        }
    }
}