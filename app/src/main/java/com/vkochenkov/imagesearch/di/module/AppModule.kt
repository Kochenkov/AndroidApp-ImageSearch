package com.vkochenkov.imagesearch.di.module

import android.app.Application
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.data.api.ApiService
import com.vkochenkov.imagesearch.data.db.FavouriteImagesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = app

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, dao: FavouriteImagesDao): Repository = Repository(apiService, dao)
}