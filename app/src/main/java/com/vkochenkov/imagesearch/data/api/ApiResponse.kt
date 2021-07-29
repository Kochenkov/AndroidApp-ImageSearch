package com.vkochenkov.imagesearch.data.api

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val total: Int,
    val totalHints: Int,
    val hints: List<Hint>
) {
    data class Hint(
        val id: Int,
        val pageURL: String,
        val type: String,
        val tags: String,
        val previewURL: String,
        val previewWidth: Int,
        val previewHeight: Int,
        val webformatURL: String,
        val webformatWidth: Int,
        val webformatHeight: Int,
        val largeImageURL: String,
        val imageWidth: Int,
        val imageHeight: Int,
        val imageSize: Int,
        val views: Int,
        val downloads: Int,
        val collections: Int,
        val likes: Int,
        val comments: Int,
        @SerializedName("user_id") val userId: Long,
        val user: String,
        val userImageUR: String
    )
}
