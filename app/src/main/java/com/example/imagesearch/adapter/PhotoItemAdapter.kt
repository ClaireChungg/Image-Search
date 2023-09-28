package com.example.imagesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.GridItemBinding
import com.example.imagesearch.model.Photo
import com.example.imagesearch.viewmodel.LayoutType

class PhotoItemAdapter : ListAdapter<Photo, RecyclerView.ViewHolder>(DiffCallback) {
    private val viewTypeList = 0
    private val viewTypeGrid = 1
    private var viewType = LayoutType.LIST

    class GridItemViewHolder(private var binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    fun setViewType(viewType: LayoutType) {
        this.viewType = viewType
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewType == LayoutType.GRID) viewTypeGrid else viewTypeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridItemViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GridItemViewHolder).bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.previewURL == newItem.previewURL
        }
    }
}