package com.vkochenkov.imagesearch.presentation.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.data.model.NetworkState
import com.vkochenkov.imagesearch.presentation.activity.ImageActivity
import com.vkochenkov.imagesearch.presentation.adapter.ImageViewHolder
import com.vkochenkov.imagesearch.presentation.adapter.ImagesAdapter
import com.vkochenkov.imagesearch.presentation.adapter.ItemClickListener
import com.vkochenkov.imagesearch.presentation.view_model.HomeViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var emptyListTv: TextView
    private lateinit var progressBar: ProgressBar

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

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initFields(root)
        initRecyclerView(root)
        initLiveDataObservers()
        return root
    }

    private fun initFields(view: View) {
        imagesRecyclerView = view.findViewById(R.id.images_list)
        progressBar = view.findViewById(R.id.progress_home)
        emptyListTv = view.findViewById(R.id.tv_empty_list)
    }

    private fun initLiveDataObservers() {
        homeViewModel.itemsList.observe(viewLifecycleOwner, Observer {
            (imagesRecyclerView.adapter as ImagesAdapter).setItemsList(it)
            (imagesRecyclerView.adapter as ImagesAdapter).notifyDataSetChanged()
        })
        homeViewModel.networkState.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                NetworkState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                NetworkState.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        activity,
                        activity?.applicationContext?.getText(R.string.error_network_text),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                NetworkState.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun initRecyclerView(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        } else {
            imagesRecyclerView.layoutManager = GridLayoutManager(view.context, 3)
        }
        imagesRecyclerView.adapter = ImagesAdapter(object : ItemClickListener {
            override fun onItemCLick(holder: ImageViewHolder, item: ImageItem) {
                val intent = Intent(activity, ImageActivity::class.java).apply {
                    putExtra(IMAGE_ITEM, item)
                }
                startActivity(intent)
            }
        })
    }
}