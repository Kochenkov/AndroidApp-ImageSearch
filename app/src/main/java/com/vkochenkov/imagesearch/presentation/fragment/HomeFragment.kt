package com.vkochenkov.imagesearch.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.presentation.view_model.HomeViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
//    @Inject
//    lateinit var imageLoader: ImageLoader


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
        val imageView = root.findViewById<ImageView>(R.id.image_view)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //  textView.text = it
//            var imageLoader = ImageLoader.getInstance()
//            imageLoader.init(ImageLoaderConfiguration.createDefault(activity))
//            imageLoader.displayImage(textView.text.toString(), imageView);

            Glide.with(requireActivity().applicationContext)
                .load(it)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

        })
        return root
    }
}