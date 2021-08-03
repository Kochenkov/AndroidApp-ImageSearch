package com.vkochenkov.imagesearch.presentation.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem


class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.iv_item)

    //todo good placeholders
    private val glideOptions: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun bind(item: ImageItem) {
        Glide.with(itemView.context.applicationContext)
            .load(item.mediumImageUrl)
            .apply(glideOptions)
            .into(imageView)
    }
}