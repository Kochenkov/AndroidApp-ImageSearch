package com.vkochenkov.imagesearch.data

import com.vkochenkov.imagesearch.data.api.ApiService
import com.vkochenkov.imagesearch.data.api.ApiService.Companion.API_KEY
import com.vkochenkov.imagesearch.data.model.ApiResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    fun getImagesFromApi(page: Int) =
        apiService.getAllImages(API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}