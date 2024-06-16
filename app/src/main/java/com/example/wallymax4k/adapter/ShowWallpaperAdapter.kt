package com.example.wallymax4k.adapter


import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.audiofx.AudioEffect.Descriptor
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallymax4k.R
import com.example.wallymax4k.databinding.ShowWallpaperItemBinding
import com.example.wallymax4k.model.WallpaperModel
import com.example.wallymax4k.room_database.FavouriteWallpaper
import com.example.wallymax4k.room_database.WallpaperDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ShowWallpaperAdapter(
    var context: Context,
    private val showWallpaperList: ArrayList<WallpaperModel>,
) : RecyclerView.Adapter<ShowWallpaperAdapter.ShowWallpaperViewHolder>() {
    private lateinit var progressBar: ProgressDialog
    private lateinit var dialog: BottomSheetDialog

    inner class ShowWallpaperViewHolder(var binding: ShowWallpaperItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowWallpaperViewHolder {
        context = parent.context
        return ShowWallpaperViewHolder(
            ShowWallpaperItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return showWallpaperList.size
    }

    override fun onBindViewHolder(holder: ShowWallpaperViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(showWallpaperList[position].wallpaper)
            .into(holder.binding.showWallpaper)

        Glide.with(holder.itemView)
            .load(showWallpaperList[position].wallpaper)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.binding.backImage)


        val database = WallpaperDatabase.getInstance(context)
         try {
             if (database.favouriteWallpaper().isExist(showWallpaperList[position].id) != null) {
                 holder.binding.favourite.setImageResource(R.drawable.wallpaper_favourite_icon)
             } else {
                 holder.binding.favourite.setImageResource(R.drawable.fav)
             }
         }catch (e:Exception){}


        val favWallpaperData = FavouriteWallpaper(
            showWallpaperList[position].id,
            showWallpaperList[position].wallpaper
        )
        try {
        holder.binding.favourite.setOnClickListener {
                if (database.favouriteWallpaper().isExist(showWallpaperList[position].id) != null) {
                    deleteToFavourite(
                        showWallpaperList[position].id,
                        showWallpaperList[position].wallpaper,
                        holder.binding.favourite,
                        database
                    )
                    holder.binding.favourite.setImageResource(R.drawable.fav)
                } else {
                    addToFavourite(
                        showWallpaperList[position].id,
                        showWallpaperList[position].wallpaper,
                        holder.binding.favourite,
                        database
                    )
                    holder.binding.favourite.setImageResource(R.drawable.wallpaper_favourite_icon)
                }
            }
        }catch (e:Exception){}


        // bottomSheet Open code
        holder.binding.showBottomSheet.setOnClickListener {
            progressBar = ProgressDialog(context)
            progressBar.setTitle("Please Wait")
            progressBar.setMessage("set screen wallpaper 😀😀😀")
            progressBar.setCancelable(false)
            progressBar.setCanceledOnTouchOutside(false)
            openBottomSheetDialog(position)
        }
    }
    private fun addToFavourite(
        id: String,
        wallpaper: String,
        favourite: ImageView,
        database: WallpaperDatabase
    ) {
        try {
            val wallpaperData = FavouriteWallpaper(id,wallpaper)
          CoroutineScope(Dispatchers.IO).launch {
              database.favouriteWallpaper().insert(wallpaperData)
          }
        }catch (e:Exception){}
    }

    private fun deleteToFavourite(
        id: String,
        wallpaper: String,
        favourite: ImageView,
       database: WallpaperDatabase
    ) {
       try {
           CoroutineScope(Dispatchers.IO).launch{
               val wallpaperData = FavouriteWallpaper(id,wallpaper)
               database.favouriteWallpaper().deleteFav(wallpaperData)

           }
       }catch (e:Exception){}
    }

    private fun openBottomSheetDialog(position: Int) {
        dialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null)
        dialog.setContentView(view)
        val lookScreenWallpaper = view.findViewById<LinearLayout>(R.id.setLookScreen)
        val setHomeScreen = view.findViewById<LinearLayout>(R.id.setHomeScreen)
        val bottomSheetDialog = view.findViewById<Button>(R.id.bottomSheetDialogCancel)
        setHomeScreen.setOnClickListener {
            WallpaperDownloader(progressBar).execute(showWallpaperList[position].wallpaper)
            progressBar.show()
            dialog.dismiss()
        }
        lookScreenWallpaper.setOnClickListener {
            setLockScreenWallpaper(context, showWallpaperList[position].wallpaper)
            dialog.dismiss()
            progressBar.show()
        }

        bottomSheetDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private inner class WallpaperDownloader(private val progressDialog: ProgressDialog) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlString = urls[0]
            var bitmap: Bitmap? = null
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            progressDialog.dismiss()

            if (result != null) {
                // Set wallpaper to fit home screen
                try {
                    val wallpaperManager = WallpaperManager.getInstance(context)
                    wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_SYSTEM)
                    progressDialog.dismiss()
                    Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    progressDialog.dismiss()
                    Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            } else {
                progressDialog.dismiss()
                Toast.makeText(context, "Failed to download wallpaper", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setLockScreenWallpaper(context: Context, wallpaperUrl: String) {
        // Show progress dialog

        // Download wallpaper in background thread
        WallpaperDownloader(context, progressBar).execute(wallpaperUrl)
    }

}


private class WallpaperDownloader(
    private val context: Context,
    private val progressDialog: ProgressDialog,
) : AsyncTask<String, Void, Bitmap?>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlString = urls[0]
        var bitmap: Bitmap? = null
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        progressDialog.dismiss()

        if (result != null) {
            // Set wallpaper for lock screen
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(result, null, true, WallpaperManager.FLAG_LOCK)
                progressDialog.dismiss()
                Toast.makeText(
                    context,
                    "Lock screen wallpaper set successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                progressDialog.dismiss()
                Toast.makeText(context, "Failed to set lock screen wallpaper", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            progressDialog.dismiss()
            Toast.makeText(context, "Failed to download wallpaper", Toast.LENGTH_SHORT).show()
        }
    }
}