package com.vkochenkov.imagesearch.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.model.ApiResponse
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.data.model.NetworkState
import com.vkochenkov.imagesearch.data.utils.Mapper
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ImageViewModel @Inject constructor() : ViewModel() {

//    private val _itemsList = MutableLiveData<List<ImageItem>>()
//    val itemsList: LiveData<List<ImageItem>> = _itemsList

    fun onCreateView() {
    }

}