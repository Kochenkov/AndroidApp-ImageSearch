package com.vkochenkov.imagesearch.di.module

import android.app.Application
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = app

    @Provides
    fun provideRepository(): Repository = Repository()
}