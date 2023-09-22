package com.example.imagesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.GridItemBinding
import com.example.imagesearch.databinding.ListItemBinding
import com.example.imagesearch.model.Photo
import com.example.imagesearch.viewmodel.LayoutType
import com.example.imagesearch.viewmodel.PhotoViewModel

class PhotoItemAdapter(private val viewModel: PhotoViewModel) :
    ListAdapter<Photo, RecyclerView.ViewHolder>(DiffCallback) {
    private val viewTypeList = 0
    private val viewTypeGrid = 1

    class ListItemViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    class GridItemViewHolder(private var binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewModel.layoutType.value == LayoutType.GRID) viewTypeGrid else viewTypeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == viewTypeList) {
            ListItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
        } else {
            GridItemViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (getItemViewType(position) == viewTypeList) (holder as ListItemViewHolder).bind(item)
        else (holder as GridItemViewHolder).bind(item)
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