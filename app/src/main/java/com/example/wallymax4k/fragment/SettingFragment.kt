package com.example.wallymax4k.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallymax4k.R
import com.example.wallymax4k.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
  private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentSettingBinding.inflate(layoutInflater)


        binding.backToMoveSettingToHomeFav.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }
}