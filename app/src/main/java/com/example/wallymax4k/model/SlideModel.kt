package com.example.wallymax4k.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SlideModel(
    val id:String,
    val slideImage:String
)   : Parcelable {
    constructor() : this("","")
}
