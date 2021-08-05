package com.vkochenkov.imagesearch.data

import com.vkochenkov.imagesearch.data.api.ApiService
import com.vkochenkov.imagesearch.data.api.ApiService.Companion.API_KEY
import com.vkochenkov.imagesearch.data.db.FavouriteImagesDao
import com.vkochenkov.imagesearch.data.model.ImageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: FavouriteImagesDao
) {

    fun getImagesFromApi(page: Int) =
        apiService.getAllImages(API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAllItemsFromFavorites() =
        dao.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun isFavouriteItem(): Boolean {
        return false
        //todo
    }

    fun getItemFromFavourites(id: Int) =
        dao.getImage(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteItemFromFavourites(image: ImageItem) {
        dao.deleteImage(image)
    }

    fun addItemToFavourites(image: ImageItem) {
        dao.insertImage(image)
    }

}