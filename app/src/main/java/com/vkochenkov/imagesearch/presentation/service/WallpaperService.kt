package com.vkochenkov.imagesearch.presentation.service

import android.app.IntentService
import android.app.WallpaperManager
import android.content.Intent
import com.vkochenkov.imagesearch.presentation.receiver.WallpaperBroadcastReceiver
import com.vkochenkov.imagesearch.presentation.utils.GlideLoader

class WallpaperService : IntentService("WallpaperService") {

    override fun onHandleIntent(intent: Intent?) {

        val wallpaperManager = WallpaperManager.getInstance(applicationContext)

        try {
            sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
                it.action = WallpaperBroadcastReceiver.WALLPAPER_LOADING_STARTED
            })
            wallpaperManager.setBitmap(GlideLoader.bitmapImage)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(GlideLoader.bitmapImage, null, true, WallpaperManager.FLAG_LOCK)
            }
            sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
                it.action = WallpaperBroadcastReceiver.WALLPAPER_SUCCESS
            })
        } catch (e: Exception) {
            e.printStackTrace()
            sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
                it.action = WallpaperBroadcastReceiver.WALLPAPER_ERROR
            })
        } finally {
            stopSelf()
        }
    }
}