package com.vkochenkov.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.presentation.view_model.FavouritesViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class FavouritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favouritesViewModel: FavouritesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FavouritesViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        App.appComponent.inject(this)

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        favouritesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}