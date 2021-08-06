package com.vkochenkov.imagesearch.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.BitmapStorage
import javax.inject.Inject

class ImageLoader(
    val context: Context,
    private val imageUrl: String,
    private val imageView: SubsamplingScaleImageView,
    private val emptyDataTextView: TextView
) {

    @Inject
    lateinit var networkChecker: NetworkChecker

    init {
        App.appComponent.inject(this)
    }

    private val requestOptions: RequestOptions = RequestOptions().centerInside()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    private var progressBar: ProgressBar? = null

    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    fun loadImage() {
        if (networkChecker.isOnline()) {
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .listener(glideRequestListener())
                .into(glideCustomTarget())
        } else {
            progressBar?.visibility = View.INVISIBLE
            emptyDataTextView.visibility = View.VISIBLE
            BitmapStorage.bitmapImage = null
            showErrorNetworkToast(R.string.no_network_error_text)
        }
    }

    private fun glideCustomTarget() = object : CustomTarget<Bitmap>() {
        override fun onResourceReady(
            resource: Bitmap,
            transition: Transition<in Bitmap>?
        ) {
            imageView.setImage(ImageSource.cachedBitmap(resource))
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    private fun glideRequestListener() = object : RequestListener<Bitmap?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap?>?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar?.visibility = View.INVISIBLE
            emptyDataTextView.visibility = View.VISIBLE
            BitmapStorage.bitmapImage = null
            showErrorNetworkToast(R.string.load_network_error_text)
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar?.visibility = View.INVISIBLE
            emptyDataTextView.visibility = View.INVISIBLE
            BitmapStorage.bitmapImage = resource
            return false
        }
    }

    private fun showErrorNetworkToast(strId: Int) {
        Toast.makeText(context, context.applicationContext?.getText(strId), Toast.LENGTH_SHORT)
            .show()
    }
}