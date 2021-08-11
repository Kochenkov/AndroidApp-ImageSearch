package com.vkochenkov.imagesearch.presentation.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.api.NetworkState
import com.vkochenkov.imagesearch.data.api.PaggingStorage
import com.vkochenkov.imagesearch.data.model.ImageItem
import com.vkochenkov.imagesearch.di.App
import com.vkochenkov.imagesearch.di.App.Companion.IMAGE_ITEM
import com.vkochenkov.imagesearch.presentation.activity.ImageActivity
import com.vkochenkov.imagesearch.presentation.adapter.ImageViewHolder
import com.vkochenkov.imagesearch.presentation.adapter.ImagesAdapter
import com.vkochenkov.imagesearch.presentation.adapter.ItemClickListener
import com.vkochenkov.imagesearch.presentation.showToast
import com.vkochenkov.imagesearch.presentation.view_model.ImagesViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class ImagesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var emptyListTv: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val imagesViewModel: ImagesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(ImagesViewModel::class.java)
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

        imagesViewModel.onCreateView()

        val root = inflater.inflate(R.layout.fragment_images, container, false)
        initViews(root)
        initRecyclerView(root)
        initLiveDataObservers()
        setListeners()

        return root
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            imagesViewModel.onSwipeRefresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.images_progress)
        emptyListTv = view.findViewById(R.id.images_empty_tv)
        swipeRefreshLayout = view.findViewById(R.id.images_swipe_refresh)
    }

    private fun initLiveDataObservers() {
        imagesViewModel.networkState.observe(viewLifecycleOwner, Observer {
            emptyListTv.visibility = View.VISIBLE
            when (it) {
                NetworkState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    emptyListTv.visibility = View.INVISIBLE
                    PaggingStorage.canDoCallNow = false
                }
                NetworkState.LOADING_ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    PaggingStorage.canDoCallNow = true
                    showToast(R.string.load_network_error_text)
                }
                NetworkState.NO_INTERNET_CONNECTION -> {
                    progressBar.visibility = View.INVISIBLE
                    PaggingStorage.canDoCallNow = true
                    showToast(R.string.no_network_error_text)
                }
                NetworkState.SUCCESS -> {
                    PaggingStorage.canDoCallNow = true
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
        imagesViewModel.itemsList.observe(viewLifecycleOwner, Observer {
            (imagesRecyclerView.adapter as ImagesAdapter).setItemsList(it)
            (imagesRecyclerView.adapter as ImagesAdapter).notifyDataSetChanged()
            checkItemsListSize()
        })
    }

    //todo баг с одновременным отображением списка и заглушки
    private fun checkItemsListSize() {
        if (imagesViewModel.itemsList.value == null) {
            emptyListTv.visibility = View.VISIBLE
        } else {
            emptyListTv.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView(view: View) {
        imagesRecyclerView = view.findViewById(R.id.images_recycler)

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

        imagesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = imagesRecyclerView.layoutManager!!.childCount
                    val totalItemCount = imagesRecyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems =
                        (imagesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        //todo тут серьезная недоработка по вызову кол-ва страниц
                            if (PaggingStorage.canDoCallNow) {
                                imagesViewModel.onPaggingScroll()

                            }
                    }
                }
            }
        })
    }
}