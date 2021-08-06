package com.vkochenkov.imagesearch.data.api.dto

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
) {
    data class Hit(
        val id: Int,
        @SerializedName("pageURL") val pageUrl: String,
        val type: String,
        val tags: String,
        @SerializedName("previewURL") val previewUrl: String,
        val previewWidth: Int,
        val previewHeight: Int,
        @SerializedName("webformatURL") val webformatUrl: String,
        val webformatWidth: Int,
        val webformatHeight: Int,
        @SerializedName("largeImageURL") val largeImageUrl: String,
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
        @SerializedName("userImageURL") val userImageUrl: String
    )
}
