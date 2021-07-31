package com.vkochenkov.imagesearch.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.model.ApiResponse
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    //todo
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun onCreateView() {
        repository.getAllImagesFromApi(singleObserver())
        Log.d("testtt", "onCrView")

    }

    fun singleObserver() = object : SingleObserver<ApiResponse> {
        override fun onSubscribe(d: Disposable) {
            Log.d("testtt", "onSub")
        }

        override fun onSuccess(t: ApiResponse) {
            _text.postValue(t.hits[0].previewUrl)
            Log.d("testtt", "onSucc")
        }

        override fun onError(e: Throwable) {
            Log.d("testtt", "onError")
        }
    }
}