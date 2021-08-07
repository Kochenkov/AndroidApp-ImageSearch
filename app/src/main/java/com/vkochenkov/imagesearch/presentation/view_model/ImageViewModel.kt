package com.vkochenkov.imagesearch.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.model.ImageItem
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ImageViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _isFavouriteImage = MutableLiveData<Boolean>()
    val isFavouriteImage: LiveData<Boolean> = _isFavouriteImage

    fun onCreate(item: ImageItem) {
        isImageFavourite(item)
    }

    fun onLikeButtonClick(item: ImageItem) {
        if (_isFavouriteImage.value!!) {
            repository.deleteItemFromFavourites(item).subscribe(changeFavouriteRxObserver(item))
        } else {
            repository.addItemToFavourites(item).subscribe(changeFavouriteRxObserver(item))
        }
    }

    private fun isImageFavourite(item: ImageItem) {
        repository.getItemFromFavourites(item.id).subscribe(itemsRxObserver())
    }

    private fun itemsRxObserver() = object : MaybeObserver<ImageItem> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(item: ImageItem) {
            _isFavouriteImage.postValue(true)
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {
            _isFavouriteImage.postValue(false)
        }
    }

    private fun changeFavouriteRxObserver(item: ImageItem) = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {}

        override fun onComplete() {
            isImageFavourite(item)
        }
    }
}