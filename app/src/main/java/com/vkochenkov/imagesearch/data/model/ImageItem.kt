package com.vkochenkov.imagesearch.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(
    val id: Int,
    val type: String,
    val tags: List<String>,
    val littleImageUrl: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
): Parcelable

