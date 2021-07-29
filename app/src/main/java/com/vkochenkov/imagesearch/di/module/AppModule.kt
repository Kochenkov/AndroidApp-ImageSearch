package com.vkochenkov.imagesearch.di.module

import android.app.Application
import com.vkochenkov.imagesearch.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = app
}