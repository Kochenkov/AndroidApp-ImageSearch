package com.vkochenkov.imagesearch.data

import com.vkochenkov.imagesearch.data.model.ApiResponse
import com.vkochenkov.imagesearch.data.api.ApiService
import com.vkochenkov.imagesearch.data.api.ApiService.Companion.API_KEY
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    fun getAllImagesFromApi(singleObserver: SingleObserver<ApiResponse>) {
        apiService.getAllImages(API_KEY, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(singleObserver)
    }
}