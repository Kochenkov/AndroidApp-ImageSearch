package com.vkochenkov.imagesearch.di.component

import com.vkochenkov.imagesearch.data.Repository
import com.vkochenkov.imagesearch.presentation.utils.ImageLoader
import com.vkochenkov.imagesearch.di.module.AppModule
import com.vkochenkov.imagesearch.di.module.DatabaseModule
import com.vkochenkov.imagesearch.di.module.NetworkModule
import com.vkochenkov.imagesearch.presentation.activity.MainActivity
import com.vkochenkov.imagesearch.presentation.fragment.AppInfoFragment
import com.vkochenkov.imagesearch.presentation.fragment.FavouritesFragment
import com.vkochenkov.imagesearch.presentation.fragment.ImagesFragment

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: ImagesFragment)
    fun inject(fragment: FavouritesFragment)
    fun inject(fragment: AppInfoFragment)
    fun inject(repository: Repository)
    fun inject(imageLoader: ImageLoader)
}