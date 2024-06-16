package com.example.wallymax4k.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WallpaperModel(
    val id:String,
    val wallpaper:String=""
) : Parcelable {
    constructor():this("","")
}

