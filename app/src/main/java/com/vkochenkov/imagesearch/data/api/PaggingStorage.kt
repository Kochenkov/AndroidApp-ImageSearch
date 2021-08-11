package com.vkochenkov.imagesearch.data.api

import android.util.Log

object PaggingStorage {

    const val hitsPerPage = 20
    //инициализируется при первом запросе
    var totalHits: Int = 20
    var maxPage: Int = 1
    var currentPage: Int = 1
    var canDoCallNow = true

    fun incrementPagesSize(): Boolean {
        Log.d("PAGE LOGGG", currentPage.toString())
        maxPage = totalHits / hitsPerPage + 1
        return if (currentPage < maxPage) {
            currentPage++
            true
        } else {
            false
        }
    }
}