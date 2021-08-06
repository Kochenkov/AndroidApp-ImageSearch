package com.vkochenkov.imagesearch.data.db

import androidx.room.*
import com.vkochenkov.imagesearch.data.model.ImageItem
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface FavouriteImagesDao {

    @Query("SELECT * FROM Images")
    fun getImages(): Maybe<List<ImageItem>>

    @Query("SELECT * FROM Images WHERE id = :id")
    fun getImage(id: Int): Maybe<ImageItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(item: ImageItem): Completable

    @Delete()
    fun deleteImage(item: ImageItem): Completable
}