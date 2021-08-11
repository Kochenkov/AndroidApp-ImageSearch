package com.vkochenkov.imagesearch.data.api

import android.util.Log
import com.vkochenkov.imagesearch.data.model.ImageItem

object PaggingStorage {

    const val hitsPerPage = 20
    //инициализируется при первом и каждом последующем запросе
    var totalHits: Int = 20
    //пересчитывается при каждой попытке сделать запрос с пагинацией
    var maxPage: Int = 1
    //становится 1 в методах рефреша и старта фрагмента, он onSuccess увеличивается
    var currentPage: Int = 1
    //если идет LOADING state, то false иначе true
    var canDoCallNow = true
    //для пагинации
    val temporaryDataList = ArrayList<ImageItem>()

    fun isHavePages(): Boolean {
        maxPage = (totalHits / hitsPerPage) + 1
        return currentPage < maxPage
    }
}