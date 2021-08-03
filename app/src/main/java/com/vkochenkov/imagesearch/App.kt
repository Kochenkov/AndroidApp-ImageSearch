package com.vkochenkov.imagesearch

import android.app.Application
import com.vkochenkov.imagesearch.di.component.AppComponent
import com.vkochenkov.imagesearch.di.component.DaggerAppComponent
import com.vkochenkov.imagesearch.di.module.AppModule
import com.vkochenkov.imagesearch.di.module.NetworkModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
        //constants
        const val IMAGE_ITEM = "IMAGE_ITEM"
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }
}