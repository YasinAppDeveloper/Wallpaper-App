package com.example.wallymax4k.room_database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import javax.annotation.Nonnull

@Parcelize
@Entity(tableName = "favourite_wallpaper")
data class FavouriteWallpaper(
    @PrimaryKey
    @Nonnull
    val id:String,
    val url:String
) : Parcelable {
    constructor() : this("","")
}
