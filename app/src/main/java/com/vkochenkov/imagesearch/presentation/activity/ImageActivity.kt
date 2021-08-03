package com.vkochenkov.imagesearch.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vkochenkov.imagesearch.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem

class ImageActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        imageView = findViewById(R.id.iv_full_image)

        val item = intent.getParcelableExtra<ImageItem>(IMAGE_ITEM)

        //todo add loader
        val options: RequestOptions = RequestOptions()
            .centerInside()
            .placeholder(R.drawable.ic_baseline_image_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(this)
            .load(item?.largeImageUrl)
            .apply(options)
            .into(imageView)
    }
}