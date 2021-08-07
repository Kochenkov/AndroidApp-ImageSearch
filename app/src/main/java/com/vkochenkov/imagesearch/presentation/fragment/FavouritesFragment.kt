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
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.db.DbState
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.presentation.activity.ImageActivity
import com.vkochenkov.imagesearch.presentation.adapter.ImageViewHolder
import com.vkochenkov.imagesearch.presentation.adapter.ImagesAdapter
import com.vkochenkov.imagesearch.presentation.adapter.ItemClickListener
import com.vkochenkov.imagesearch.presentation.view_model.FavouritesViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class FavouritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var emptyListTv: TextView
    private lateinit var progressBar: ProgressBar


    private val favouritesViewModel: FavouritesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(FavouritesViewModel::class.java)
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

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        initViews(root)
        initRecyclerView(root)
        initLiveDataObservers()
        return root
    }

    override fun onResume() {
        super.onResume()
        favouritesViewModel.onResume()
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.favourites_progress)
        emptyListTv = view.findViewById(R.id.favourites_empty_tv)
    }

    private fun initLiveDataObservers() {
        favouritesViewModel.dbState.observe(viewLifecycleOwner, Observer {
            emptyListTv.visibility = View.VISIBLE
            when (it) {
                DbState.GETTING -> {
                    progressBar.visibility = View.VISIBLE
                    emptyListTv.visibility = View.INVISIBLE
                }
                DbState.GETTING_ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    showErrorToast(R.string.load_db_error_text)
                }
                DbState.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
        favouritesViewModel.favouritesList.observe(viewLifecycleOwner, Observer {
            (favouritesRecyclerView.adapter as ImagesAdapter).setItemsList(it)
            (favouritesRecyclerView.adapter as ImagesAdapter).notifyDataSetChanged()
            checkItemsListSize()
        })
    }

    private fun showErrorToast(strId: Int) {
        Toast.makeText(activity, activity?.applicationContext?.getText(strId), Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkItemsListSize() {
        if (favouritesViewModel.favouritesList.value?.size == 0 || favouritesViewModel.favouritesList.value == null) {
            emptyListTv.visibility = View.VISIBLE
        } else {
            emptyListTv.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView(view: View) {
        favouritesRecyclerView = view.findViewById(R.id.favourites_recycler)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            favouritesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        } else {
            favouritesRecyclerView.layoutManager = GridLayoutManager(view.context, 3)
        }
        favouritesRecyclerView.adapter = ImagesAdapter(object : ItemClickListener {
            override fun onItemCLick(holder: ImageViewHolder, item: ImageItem) {
                val intent = Intent(activity, ImageActivity::class.java).apply {
                    putExtra(App.IMAGE_ITEM, item)
                }
                startActivity(intent)
            }
        })
    }
}