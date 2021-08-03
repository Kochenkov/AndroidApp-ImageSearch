package com.vkochenkov.imagesearch.presentation.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.R
import javax.inject.Inject

class GlideLoader(val context: Context,
                  private val imageUrl: String,
                  private val imageView: ImageView,
                  private val errorTextView: TextView) {

    @Inject
    lateinit var networkChecker: NetworkChecker

    init {
        App.appComponent.inject(this)
    }

    private val requestOptions: RequestOptions = RequestOptions()
        .centerInside()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    private var progressBar: ProgressBar? = null

    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    fun loadImage() {
        if (networkChecker.isOnline()) {
            Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .listener(glideRequestListener())
                .into(imageView)
        } else {
            progressBar?.visibility = View.INVISIBLE
            errorTextView.text = context.getText(R.string.no_network_error_text)
            errorTextView.visibility = View.VISIBLE
        }
    }

    private fun glideRequestListener(): RequestListener<Drawable?> {
        return object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.INVISIBLE
                errorTextView.text = context.getText(R.string.load_network_error_text)
                errorTextView.visibility = View.VISIBLE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.INVISIBLE
                errorTextView.visibility = View.INVISIBLE
                return false
            }
        }
    }
}