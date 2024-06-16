package com.example.wallymax4k.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteWallpaper::class], version = 2)
abstract class WallpaperDatabase : RoomDatabase() {

    companion object{
        private var database : WallpaperDatabase? = null
        private var DATABASE_NAME = "wallpaper_database"

        @Synchronized
        fun getInstance(context: Context) : WallpaperDatabase{
            if (database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    WallpaperDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }

    }

    abstract fun favouriteWallpaper() : WallpaperDao



}