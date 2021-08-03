package com.vkochenkov.imagesearch.presentation.adapter

import com.vkochenkov.imagesearch.data.model.ImageItem

interface ItemClickListener {
    fun onItemCLick(holder: ImageViewHolder, item: ImageItem)
}