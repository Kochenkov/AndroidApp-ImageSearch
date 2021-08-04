package com.vkochenkov.imagesearch.presentation.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.vkochenkov.imagesearch.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.presentation.service.WallpaperService
import com.vkochenkov.imagesearch.presentation.receiver.WallpaperBroadcastReceiver
import com.vkochenkov.imagesearch.presentation.utils.GlideLoader

class ImageActivity : AppCompatActivity() {

    private lateinit var imageView: SubsamplingScaleImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyDataTv: TextView
    private lateinit var button: Button

    private lateinit var glideLoader: GlideLoader

    private lateinit var wallpaperBroadcastReceiver: WallpaperBroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        initViews()
        initReceiver()
        supportActionBar?.hide()

        val item = intent.getParcelableExtra<ImageItem>(IMAGE_ITEM)
        val url = item?.largeImageUrl.toString()

        glideLoader = GlideLoader(this, url, imageView, emptyDataTv)
        glideLoader.setProgressBar(progressBar)
        glideLoader.loadImage()

        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(wallpaperBroadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(wallpaperBroadcastReceiver)
    }

    private fun setOnClickListeners() {
        button.setOnClickListener {
            val intent = Intent(this, WallpaperService::class.java)
            startService(intent)
        }
    }

    private fun initViews() {
        imageView = findViewById(R.id.iv_full_image)
        progressBar = findViewById(R.id.progress_detail)
        emptyDataTv = findViewById(R.id.tv_empty_details)
        button = findViewById(R.id.button)
    }

    private fun initReceiver() {
        wallpaperBroadcastReceiver = WallpaperBroadcastReceiver()
        intentFilter = IntentFilter()
        intentFilter.addAction(WallpaperBroadcastReceiver.WALLPAPER_ERROR)
        intentFilter.addAction(WallpaperBroadcastReceiver.WALLPAPER_SUCCESS)
        intentFilter.addAction(WallpaperBroadcastReceiver.WALLPAPER_LOADING_STARTED)
    }
}