package com.vkochenkov.imagesearch.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.vkochenkov.imagesearch.R

class WallpaperBroadcastReceiver: BroadcastReceiver() {

    companion object {
        const val WALLPAPER_LOADING_STARTED = "com.vkochenkov.WALLPAPER_LOADING_STARTED"
        const val WALLPAPER_SUCCESS = "com.vkochenkov.WALLPAPER_SUCCESS"
        const val WALLPAPER_ERROR = "com.vkochenkov.WALLPAPER_ERROR"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            WALLPAPER_ERROR -> showToast(context, R.string.wallpaper_error_str)
            WALLPAPER_SUCCESS -> showToast(context, R.string.wallpaper_success_str)
            WALLPAPER_LOADING_STARTED -> showToast(context, R.string.wallpaper_loading_str)
        }
    }

    private fun showToast(context: Context?, strId: Int) {
        Toast.makeText(context, context?.getText(strId), Toast.LENGTH_SHORT)
            .show()
    }
}