package com.vkochenkov.imagesearch.data.api

object PagesStorage {

    const val hitsPerPage = 20
    var hits: Int = 1
    var maxPage: Int = 1
    var currentPage: Int = 1
    var nextPage: Int = 1

    fun validatePagesSize(totalHits: Int) {
        hits = totalHits
        maxPage = hits/hitsPerPage + 1
        if (currentPage<maxPage) {
            currentPage++
        }
    }

}