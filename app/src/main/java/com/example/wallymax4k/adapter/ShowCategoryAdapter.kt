package com.example.wallymax4k.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallymax4k.activity.DisplayCategoryActivity
import com.example.wallymax4k.R
import com.example.wallymax4k.model.CategoryModel

class ShowCategoryAdapter(var context: Context, var catList:List<CategoryModel>): RecyclerView.Adapter<ShowCategoryAdapter.ShowCategoryViewHolder>() {

    inner class ShowCategoryViewHolder(view:View) : RecyclerView.ViewHolder(view){
       val imageOfCategoryShowWallpaper = itemView.findViewById<ImageView>(R.id.categoryImageShow)
        val categoryTextOfName = itemView.findViewById<TextView>(R.id.categoryNameDisplay)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCategoryViewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.cate_show_item,parent,false)
        return ShowCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: ShowCategoryViewHolder, position: Int) {
       val catPos:CategoryModel = catList[position]
        Glide.with(context)
            .load(catPos.imageOfCategory)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageOfCategoryShowWallpaper)

        holder.categoryTextOfName.text = catPos.nameOfCategory

        holder.imageOfCategoryShowWallpaper.setOnClickListener {
          val intent = Intent(context, DisplayCategoryActivity::class.java)
            intent.putExtra("id",catPos.id)
            intent.putExtra("name",catPos.nameOfCategory)
            context.startActivity(intent)
        }


    }
    fun searchFilteredList(filter:List<CategoryModel>){
        catList = filter
        notifyDataSetChanged()
    }
}