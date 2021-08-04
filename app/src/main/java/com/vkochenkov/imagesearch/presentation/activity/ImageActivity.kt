package com.vkochenkov.imagesearch.presentation.activity

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.vkochenkov.imagesearch.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.presentation.utils.GlideLoader

class ImageActivity : AppCompatActivity() {

    lateinit var imageView: SubsamplingScaleImageView
    lateinit var progressBar: ProgressBar
    lateinit var emptyDataTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        imageView = findViewById(R.id.iv_full_image)
        progressBar = findViewById(R.id.progress_detail)
        emptyDataTv = findViewById(R.id.tv_empty_details)

        val item = intent.getParcelableExtra<ImageItem>(IMAGE_ITEM)
        val url = item?.largeImageUrl.toString()
        val glideLoader = GlideLoader(this, url, imageView,  emptyDataTv)
        glideLoader.setProgressBar(progressBar)
        glideLoader.loadImage()
    }
}