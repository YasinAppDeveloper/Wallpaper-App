package com.example.wallymax4k.model

data class CategoryModel(
    val id:String,
    val nameOfCategory:String,
    val imageOfCategory:String
){
   constructor() : this("","","")
}
