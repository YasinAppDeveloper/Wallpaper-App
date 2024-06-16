package com.example.wallymax4k.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallymax4k.activity.DisplayCategoryActivity
import com.example.wallymax4k.databinding.CategoryWallpaperItemBinding
import com.example.wallymax4k.model.CategoryModel

class CategoryAdapter(val context: Context, private val listOfCategory:ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: CategoryWallpaperItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
      return CategoryViewHolder(CategoryWallpaperItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount() : Int{
        return listOfCategory.size
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       Glide.with(context).load(listOfCategory[position].imageOfCategory)
           .transition(DrawableTransitionOptions.withCrossFade())
           .into(holder.binding.categoryImage)
        holder.binding.categoryName.text = listOfCategory[position].nameOfCategory

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayCategoryActivity::class.java)
            intent.putExtra("id",listOfCategory[position].id)
            intent.putExtra("name",listOfCategory[position].nameOfCategory)
            context.startActivity(intent)
        }

    }
}