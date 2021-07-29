package com.vkochenkov.imagesearch.di.component

import com.vkochenkov.imagesearch.di.module.AppModule
import com.vkochenkov.imagesearch.di.module.NetworkModule
import com.vkochenkov.imagesearch.presentation.MainActivity

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}