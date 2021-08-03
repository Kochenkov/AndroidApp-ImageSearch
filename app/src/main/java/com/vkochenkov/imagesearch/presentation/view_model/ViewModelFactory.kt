package com.vkochenkov.imagesearch.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.presentation.utils.NetworkChecker
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository,
                                           private val networkChecker: NetworkChecker
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, networkChecker) as T
            }
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> {
                FavouritesViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotificationsViewModel::class.java) -> {
                NotificationsViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}