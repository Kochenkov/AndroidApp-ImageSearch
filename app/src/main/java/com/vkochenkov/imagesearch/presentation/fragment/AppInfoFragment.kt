package com.vkochenkov.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.presentation.view_model.AppInfoViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class AppInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val appInfoViewModel: AppInfoViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AppInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //todo
        return null
    }
}