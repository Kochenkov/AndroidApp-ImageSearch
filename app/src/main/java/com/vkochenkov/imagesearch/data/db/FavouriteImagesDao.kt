package com.vkochenkov.imagesearch.data.db

import androidx.room.*
import com.vkochenkov.imagesearch.data.model.ImageItem
import io.reactivex.Maybe

@Dao
interface FavouriteImagesDao {

    @Query("SELECT * FROM Images")
    fun getImages(): Maybe<List<ImageItem>>

    @Query("SELECT * FROM Images WHERE id = :id")
    fun getImage(id: Int): Maybe<ImageItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(image: ImageItem)

    @Delete()
    fun deleteImage(image: ImageItem)
//
//    @Query("UPDATE Films SET isLiked = 1 WHERE serverName = :name")
//    fun setLikeFilm(name: String)
//
//    @Query("UPDATE Films SET isLiked = 0 WHERE serverName = :name")
//    fun setUnlikeFilm(name: String)
//
//    @Query("UPDATE Films SET notificationDate = :date WHERE serverName = :name")
//    fun setNotificationFilm(name: String, date: Long)
//
//    @Query("UPDATE Films SET notificationDate = 0 WHERE serverName = :name")
//    fun clearNotificationFilm(name: String)
//
//
//    @Query("SELECT * FROM Films LIMIT :pages+10")
//    fun getFilmsWithPagination(pages: Int): Maybe<List<Film>>
}