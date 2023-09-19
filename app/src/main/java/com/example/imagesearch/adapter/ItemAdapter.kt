package com.example.imagesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.example.imagesearch.databinding.GridItemBinding
import com.example.imagesearch.databinding.ListItemBinding
import com.example.imagesearch.model.Photo

class ItemAdapter : ListAdapter<Photo, ItemAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
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