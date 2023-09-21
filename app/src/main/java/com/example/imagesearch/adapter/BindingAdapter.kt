package com.example.imagesearch.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.imagesearch.R
import com.example.imagesearch.model.Photo
import com.example.imagesearch.viewmodel.ApiStatus
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}

@BindingAdapter("imageTags")
fun bindText(textView: TextView, tags: String?) {
    tags?.let {
        textView.text = tags
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Photo>?
) {
    val adapter = recyclerView.adapter as PhotoItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun bindStatus(recyclerView: RecyclerView, status: ApiStatus) {
    when (status) {
        ApiStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }

        ApiStatus.INITIAL, ApiStatus.ERROR, ApiStatus.LOADING, ApiStatus.EMPTY -> {
            recyclerView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(imageView: ImageView, status: ApiStatus) {
    when (status) {
        ApiStatus.INITIAL -> {
            imageView.load(R.drawable.baseline_image_search_24)
            imageView.visibility = View.VISIBLE
        }

        ApiStatus.ERROR -> {
            imageView.load(R.drawable.baseline_cloud_off_24)
            imageView.visibility = View.VISIBLE
        }

        ApiStatus.EMPTY -> {
            imageView.load(R.drawable.baseline_image_not_supported_24)
            imageView.visibility = View.VISIBLE
        }

        ApiStatus.LOADING, ApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(circularProgressIndicator: CircularProgressIndicator, status: ApiStatus) {
    when (status) {
        ApiStatus.LOADING -> {
            circularProgressIndicator.visibility = View.VISIBLE
        }

        ApiStatus.INITIAL, ApiStatus.ERROR, ApiStatus.DONE, ApiStatus.EMPTY -> {
            circularProgressIndicator.visibility = View.GONE
        }
    }
}