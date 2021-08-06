package com.vkochenkov.imagesearch.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.model.*
import com.vkochenkov.imagesearch.data.model.states.DbState
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _dbState = MutableLiveData<DbState>()
    val dbState: LiveData<DbState> = _dbState

    private val _favouritesList = MutableLiveData<List<ImageItem>>()
    val favouritesList: LiveData<List<ImageItem>> = _favouritesList

    private fun getFavourites() {
        repository.getAllItemsFromFavorites().subscribe(maybeRxObserver())
    }

    private fun maybeRxObserver() = object : MaybeObserver<List<ImageItem>> {
        override fun onSubscribe(d: Disposable) {
            _dbState.postValue(DbState.GETTING)
        }

        override fun onSuccess(list: List<ImageItem>) {
            _dbState.postValue(DbState.SUCCESS)
            _favouritesList.postValue(list)
        }

        override fun onError(e: Throwable) {
            _dbState.postValue(DbState.GETTING_ERROR)
        }

        override fun onComplete() {
        }
    }

    fun onResume() {
        getFavourites()
    }
}