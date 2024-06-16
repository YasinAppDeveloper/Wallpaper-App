package com.example.wallymax4k.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallymax4k.R
import com.example.wallymax4k.adapter.ShowCategoryAdapter
import com.example.wallymax4k.databinding.CategoryWallpaperItemBinding
import com.example.wallymax4k.databinding.FragmentCategoryBinding
import com.example.wallymax4k.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Collections


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var categoryAdapter: ShowCategoryAdapter
    private var categoryShowFragmentList = mutableListOf<CategoryModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)

        categoryShowFragmentList = mutableListOf()
        db = FirebaseFirestore.getInstance()

        categoryAdapter = ShowCategoryAdapter(requireContext(),categoryShowFragmentList)
        binding.recyclerViewCategoryShowInFragment.adapter = categoryAdapter

        fetchDbCategory()

        binding.searchCategory.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               filters(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
          binding.backToMoveSettingToHomeCategory.setOnClickListener {
               activity?.onBackPressed()
          }


        return binding.root
    }

    private fun filters(toString: String) {
        val searchList = categoryShowFragmentList.filter {
            it.nameOfCategory.contains(toString,ignoreCase = true)
        }
        categoryAdapter.searchFilteredList(searchList)
    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    private fun fetchDbCategory() {
        db.collection("Category")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        for (doc in document.documents) {
                            val categoryData = doc.toObject(CategoryModel::class.java)
                            categoryShowFragmentList.add(categoryData!!)
                            binding.recyclerViewCategoryShowInFragment.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE

                        }
                        Collections.shuffle(categoryShowFragmentList)
                        categoryAdapter.notifyDataSetChanged()
                    }
                }
            }
    }
}