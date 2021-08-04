package com.vkochenkov.imagesearch.data.model

import java.util.ArrayList

object Mapper {
    fun map(response: ApiResponse): List<ImageItem> {
        val imageItemsList = ArrayList<ImageItem>()
        for (hit in response.hits) {
            var imageItem = ImageItem(
                hit.id,
                hit.type,
                hit.tags.split(""),
                hit.previewUrl,
                hit.webformatUrl,
                hit.largeImageUrl
            )
            imageItemsList.add(imageItem)
        }
        return imageItemsList
    }
}