package com.vkochenkov.imagesearch.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkChecker @Inject constructor(private val context: Context) {
    fun isOnline(): Boolean {
        val connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //todo падал пару раз тут с крешом из-за нульпоинтера, странно
        return connectivityService.activeNetworkInfo != null && connectivityService.activeNetworkInfo!!.isConnectedOrConnecting
    }
}