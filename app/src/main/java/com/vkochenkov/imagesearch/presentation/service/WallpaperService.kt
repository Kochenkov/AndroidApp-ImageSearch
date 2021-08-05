package com.vkochenkov.imagesearch.presentation.service

import android.app.IntentService
import android.app.WallpaperManager
import android.content.Intent
import com.vkochenkov.imagesearch.presentation.receiver.WallpaperBroadcastReceiver
import com.vkochenkov.imagesearch.presentation.utils.ImageLoader

class WallpaperService : IntentService("WallpaperService") {

    override fun onHandleIntent(intent: Intent?) {

        //начали загрузку
        sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
            it.action = WallpaperBroadcastReceiver.WALLPAPER_LOADING_STARTED
        })

        try {
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            wallpaperManager.setBitmap(ImageLoader.bitmapImage)

            //todo разграничить
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(ImageLoader.bitmapImage, null, true, WallpaperManager.FLAG_LOCK)
            }
            //загрузка прошла успешно
            sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
                it.action = WallpaperBroadcastReceiver.WALLPAPER_SUCCESS
            })
        } catch (e: Exception) {
            //что-то пошло не так
            sendBroadcast(Intent(this, WallpaperBroadcastReceiver::class.java).also {
                it.action = WallpaperBroadcastReceiver.WALLPAPER_ERROR
            })
        } finally {
            stopSelf()
        }
    }
}