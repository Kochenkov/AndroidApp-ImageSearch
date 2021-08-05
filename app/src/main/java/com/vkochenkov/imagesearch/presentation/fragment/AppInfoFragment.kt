package com.vkochenkov.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class AppInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    //todo
//    private val favouritesViewModel: FavouritesViewModel by lazy {
//        ViewModelProvider(this, viewModelFactory).get(FavouritesViewModel::class.java)
//    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //todo
        return null
    }
}