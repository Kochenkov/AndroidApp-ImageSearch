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
import com.vkochenkov.imagesearch.presentation.view_model.HomeViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        App.appComponent.inject(this)

        homeViewModel.onCreateView()

        //todo
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}