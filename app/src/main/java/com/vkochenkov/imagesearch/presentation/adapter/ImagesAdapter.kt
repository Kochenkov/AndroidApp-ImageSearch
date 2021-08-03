package com.vkochenkov.imagesearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.data.model.ImageItem

class ImagesAdapter(val itemClickListener: ItemClickListener): RecyclerView.Adapter<ImageViewHolder>() {

    private var itemsList: List<ImageItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = itemsList[position]
        holder.bind(imageItem)
        holder.itemView.setOnClickListener { itemClickListener.onItemCLick(holder, imageItem) }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItemsList(itemsList: List<ImageItem>) {
        this.itemsList = itemsList
    }
}