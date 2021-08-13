package com.vkochenkov.imagesearch.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.api.dto.ApiResponse
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.data.api.NetworkState
import com.vkochenkov.imagesearch.data.api.PaggingStorage
import com.vkochenkov.imagesearch.data.model.Mapper
import com.vkochenkov.imagesearch.presentation.utils.NetworkChecker
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val repository: Repository,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val _itemsList = MutableLiveData<List<ImageItem>>()
    val itemsList: LiveData<List<ImageItem>> = _itemsList

    var firstFirstVisibleRecyclerPosition: Int? = null

    fun onCreateView() {
        if (_itemsList.value == null) {
            PaggingStorage.currentPage = 1
            PaggingStorage.temporaryDataList.clear()
            makeApiCall(PaggingStorage.currentPage)
        }
    }

    fun onSwipeRefresh() {
        PaggingStorage.currentPage = 1
        PaggingStorage.temporaryDataList.clear()
        makeApiCall(PaggingStorage.currentPage)
    }

    private fun getImagesFromApiRxObserver() = object : SingleObserver<ApiResponse> {
        override fun onSubscribe(d: Disposable) {
            _networkState.postValue(NetworkState.LOADING)
        }

        override fun onSuccess(r: ApiResponse) {
            PaggingStorage.totalHits = r.totalHits
            PaggingStorage.currentPage++
            PaggingStorage.temporaryDataList.addAll(Mapper.map(r))
            _networkState.postValue(NetworkState.SUCCESS)
            _itemsList.postValue(PaggingStorage.temporaryDataList)
        }

        override fun onError(e: Throwable) {
            _networkState.postValue(NetworkState.LOADING_ERROR)
        }
    }

    private fun makeApiCall(page: Int) {
        Log.d("LOGGG", "current page is: ${PaggingStorage.currentPage}")
        if (networkChecker.isOnline()) {
            repository.getImagesFromApi(page).subscribe(getImagesFromApiRxObserver())
        } else {
            _networkState.postValue(NetworkState.NO_INTERNET_CONNECTION)
        }
    }

    fun onPaggingScroll() {
        if (PaggingStorage.isHavePages()) {
            makeApiCall(PaggingStorage.currentPage)
        }
    }
}