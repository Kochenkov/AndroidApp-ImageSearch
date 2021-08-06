package com.vkochenkov.imagesearch.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.di.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.presentation.service.WallpaperService
import com.vkochenkov.imagesearch.presentation.utils.ImageLoader
import com.vkochenkov.imagesearch.presentation.view_model.ImageViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class ImageActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val imageViewModel: ImageViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ImageViewModel::class.java)
    }

    private lateinit var imageLoader: ImageLoader

    private lateinit var imageView: SubsamplingScaleImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyDataTv: TextView

    private lateinit var wallpaperBtn: ImageView
    private lateinit var likeBtn: ImageView
    private lateinit var infoBtn: ImageView
    private lateinit var downloadBtn: ImageView
    private lateinit var shareBtn: ImageView

    private lateinit var item: ImageItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        App.appComponent.inject(this)

        initViews()
        supportActionBar?.hide()

        item = intent.getParcelableExtra(IMAGE_ITEM)!!
        val url = item.largeImageUrl.toString()

        imageLoader = ImageLoader(this, url, imageView, emptyDataTv)
        imageLoader.setProgressBar(progressBar)
        imageLoader.loadImage()

        imageViewModel.onCreate(item)
        initLiveDataObservers()

        setOnClickListeners()
    }

    private fun initLiveDataObservers() {
        imageViewModel.isFavouriteImage.observe(this, Observer {
            if (it) {
                likeBtn.background =
                    AppCompatResources.getDrawable(this, R.drawable.ic_baseline_favorite_24)
            } else {
                likeBtn.background =
                    AppCompatResources.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)

            }
        })
    }

    private fun setOnClickListeners() {
        val animationRotateCenter =
            AnimationUtils.loadAnimation(this, R.anim.decreases_when_pressed)

        wallpaperBtn.setOnClickListener {
            it.startAnimation(animationRotateCenter)
            startService(Intent(this, WallpaperService::class.java))
        }
        likeBtn.setOnClickListener {
            it.startAnimation(animationRotateCenter)
            imageViewModel.changeImageFavouriteState(item)
        }
        downloadBtn.setOnClickListener {
            it.startAnimation(animationRotateCenter)
            //todo
        }
        infoBtn.setOnClickListener {
            it.startAnimation(animationRotateCenter)
            //todo
        }
        shareBtn.setOnClickListener {
            it.startAnimation(animationRotateCenter)
            //todo
        }
    }

    private fun initViews() {
        imageView = findViewById(R.id.iv_full_image)
        progressBar = findViewById(R.id.progress_detail)
        emptyDataTv = findViewById(R.id.tv_empty_details)

        wallpaperBtn = findViewById(R.id.image_wallpaper_btn)
        likeBtn = findViewById(R.id.image_like_btn)
        downloadBtn = findViewById(R.id.image_download_btn)
        shareBtn = findViewById(R.id.image_share_btn)
        infoBtn = findViewById(R.id.image_info_btn)
    }
}