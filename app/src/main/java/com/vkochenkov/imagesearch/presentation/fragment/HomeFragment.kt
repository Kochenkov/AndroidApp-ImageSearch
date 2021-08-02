package com.vkochenkov.imagesearch.presentation.fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.imagesearch.App
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.NetworkState
import com.vkochenkov.imagesearch.presentation.adapter.ImagesAdapter
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

        imagesRecyclerView = root.findViewById(R.id.images_list)
        progressBar = root.findViewById(R.id.progress_home)
        emptyListTv = root.findViewById(R.id.tv_empty_list)

        initRecyclerView(root)
        initObservers()
        return root
    }

    private fun initObservers() {
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
        //imagesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        //imagesRecyclerView.layoutManager = LinearLayoutManager(view.context)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        } else {
            imagesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        }
        imagesRecyclerView.adapter = ImagesAdapter()
//                object :
//                    FavouriteFilmsAdapter.FavouriteFilmItemClickListener {
//                    override fun detailsClickListener(film: Film) {
//                        LocalDataStore.currentSelectedFilm = film
//                        favouriteFilmsRecycler.adapter?.notifyDataSetChanged()
//                        openSelectedFilmFragment(film)
//                    }
//
//                    override fun deleteClickListener(film: Film, position: Int) {
//                        deleteItemActions(film, position)
//                        showSnackBar(film, position, view)
//                    }
//                })
    }
}