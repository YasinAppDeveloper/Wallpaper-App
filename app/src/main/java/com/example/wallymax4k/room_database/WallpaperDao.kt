package com.example.wallymax4k.room_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WallpaperDao {

    @Insert
    suspend fun insert(favouriteWallpaper: FavouriteWallpaper)

    @Delete
    suspend fun deleteFav(favouriteWallpaper: FavouriteWallpaper)

    @Query("SELECT * FROM favourite_wallpaper")
    fun getAllFavWallpaper() : LiveData<List<FavouriteWallpaper>>

    @Query("SELECT * FROM favourite_wallpaper WHERE id = :favId")
    fun isExist(favId:String) : FavouriteWallpaper

}