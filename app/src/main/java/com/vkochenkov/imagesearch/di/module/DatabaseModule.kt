package com.vkochenkov.imagesearch.di.module

import android.app.Application
import androidx.room.Room
import com.vkochenkov.imagesearch.data.db.FavouriteImagesDao
import com.vkochenkov.imagesearch.data.db.FavouriteImagesDatabase
import com.vkochenkov.imagesearch.data.db.FavouriteImagesDatabase.Companion.dbName
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Application): FavouriteImagesDatabase {
        return Room.databaseBuilder(context, FavouriteImagesDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesDao(databaseFavourite: FavouriteImagesDatabase): FavouriteImagesDao {
        return databaseFavourite.imagesDao()
    }
}