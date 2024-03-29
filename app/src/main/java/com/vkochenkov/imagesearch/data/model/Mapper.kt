package com.vkochenkov.imagesearch.data.model

import com.vkochenkov.imagesearch.data.api.dto.ApiResponse
import java.util.ArrayList

object Mapper {
    fun map(response: ApiResponse): List<ImageItem> {
        val imageItemsList = ArrayList<ImageItem>()
        for (hit in response.hits) {
            val imageItem = ImageItem(
                hit.id,
                hit.type,
                hit.tags,
                hit.previewUrl,
                hit.webformatUrl,
                hit.largeImageUrl,
                hit.user
            )
            imageItemsList.add(imageItem)
        }
        return imageItemsList
    }
}