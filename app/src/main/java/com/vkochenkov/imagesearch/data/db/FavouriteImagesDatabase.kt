package com.vkochenkov.imagesearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vkochenkov.imagesearch.data.model.ImageItem

@Database(entities = [(ImageItem::class)], version = 2)
abstract class FavouriteImagesDatabase : RoomDatabase() {

    companion object {
        val dbName = "favourite_images_db"
    }

    abstract fun imagesDao() : FavouriteImagesDao
}