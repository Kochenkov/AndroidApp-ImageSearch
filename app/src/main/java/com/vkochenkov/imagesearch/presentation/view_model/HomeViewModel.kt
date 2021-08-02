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

class HomeViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    // private val _text = MutableLiveData<String>()
    // val text: LiveData<String> = _text

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val _itemsList = MutableLiveData<List<ImageItem>>()
    val itemsList: LiveData<List<ImageItem>> = _itemsList

    fun onCreateView() {
        repository.getImagesFromApi(singleRxObserver(), 1)
    }

    private fun singleRxObserver() = object : SingleObserver<ApiResponse> {
        override fun onSubscribe(d: Disposable) {
            _networkState.postValue(NetworkState.LOADING)
        }

        override fun onSuccess(r: ApiResponse) {
            _networkState.postValue(NetworkState.SUCCESS)
            _itemsList.postValue(Mapper.map(r))
        }

        override fun onError(e: Throwable) {
            _networkState.postValue(NetworkState.ERROR)
            Log.d("Load data error: ", e.message.toString())
        }
    }
}