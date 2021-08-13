package com.vkochenkov.imagesearch.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Images")
data class ImageItem(
    @PrimaryKey val id: Int,
    val type: String,
    val tags: String,
    val littleImageUrl: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
    val userName: String
): Parcelable

