package com.vkochenkov.imagesearch.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
    }

    @GET(".")
    fun getAllImages(@Query("key") key: String,
                     @Query("page") page: Int): Single<ApiResponse>
}