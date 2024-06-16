package com.example.instagram.util

import android.app.Activity
import android.view.View
import android.view.WindowManager

object StatusBarHelper {
    fun setStatusBarColor(activity: Activity, color: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
    fun setStatusBarLightMode(activity: Activity, isLightMode: Boolean) {
        val window = activity.window
        val decorView = window.decorView
        if (isLightMode) {
            decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}