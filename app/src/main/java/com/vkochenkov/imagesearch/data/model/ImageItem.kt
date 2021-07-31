package com.vkochenkov.imagesearch.data.model

data class ImageItem(
    val id: Int,
    val type: String,
    val tags: List<String>,
    val littleImageUrl: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
)

