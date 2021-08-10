package com.vkochenkov.imagesearch.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.BitmapStorage
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.di.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.presentation.dialog.InfoBottomSheetDialog
import com.vkochenkov.imagesearch.presentation.dialog.WallpaperBottomSheetDialog
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
        val url = item.largeImageUrl

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
                    AppCompatResources.getDrawable(this, R.drawable.ic_red_favorite_24)
            } else {
                likeBtn.background =
                    AppCompatResources.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
            }
        })
    }

    private fun setOnClickListeners() {
        val animationWhenPressed =
            AnimationUtils.loadAnimation(this, R.anim.decreases_when_pressed)

        wallpaperBtn.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            val wallpaperBottomSheetDialog = WallpaperBottomSheetDialog()
            wallpaperBottomSheetDialog.show(supportFragmentManager, "WallpaperBottomSheetDialog")
        }
        likeBtn.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            imageViewModel.onLikeButtonClick(item)
        }
        downloadBtn.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            downloadImg()
        }
        infoBtn.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            val infoBottomSheetDialog = InfoBottomSheetDialog()
            infoBottomSheetDialog.show(supportFragmentManager, "InfoBottomSheetDialog")
        }
        shareBtn.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            //todo
        }
    }

    private fun downloadImg() {
        //todo проверить на апи 21
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissionArrays =
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                requestPermissions(permissionArrays, 11111)
            }

        } else {
            try {
                //todo сохраняется в pictures!!! ура, н нужно в отдельную папку
                   // плюс, нужно исправить для апи больше 30 и проверить на нем
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    BitmapStorage.bitmapImage,
                    "yourTitle",
                    "yourDescription"
                )
                Toast.makeText(this, "Image has been saved!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong: can't download image!", Toast.LENGTH_SHORT)
                    .show()
            }
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