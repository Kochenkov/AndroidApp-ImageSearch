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
import com.vkochenkov.imagesearch.data.api.ApiResponse
import com.vkochenkov.imagesearch.data.api.ApiService
import com.vkochenkov.imagesearch.presentation.view_model.HomeViewModel
import com.vkochenkov.imagesearch.presentation.view_model.ViewModelFactory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //todo test api call
//        App.appComponent.inject(this)
//
//        apiService.getAllImages("22696909-4c17dd920fd77d7daf174e4ac", 1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                object :
//                    SingleObserver<ApiResponse> {
//                    override fun onSubscribe(d: Disposable) {
//
//                    }
//
//                    override fun onSuccess(t: ApiResponse) {
//
//                    }
//
//                    override fun onError(e: Throwable) {
//
//                    }
//                })
//    }
}