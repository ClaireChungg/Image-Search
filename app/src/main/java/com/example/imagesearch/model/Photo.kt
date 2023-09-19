package com.example.imagesearch.model

import com.squareup.moshi.Json

data class PhotoResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Photo>,
)

data class Photo(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @Json(name = "webformatURL") val webFormatURL: String,
    @Json(name = "webformatWidth") val webFormatWidth: Int,
    @Json(name = "webformatHeight") val webFormatHeight: Int,
    val largeImageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    @Json(name = "user_id") val userId: Int,
    val user: String,
    val userImageURL: String
)