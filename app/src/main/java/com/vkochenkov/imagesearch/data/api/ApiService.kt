package com.vkochenkov.imagesearch.data.api

import com.vkochenkov.imagesearch.data.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
        const val API_KEY = "22696909-4c17dd920fd77d7daf174e4ac"
    }

    @GET(".")
    fun getAllImages(@Query("key") key: String,
                     @Query("page") page: Int): Single<ApiResponse>
}