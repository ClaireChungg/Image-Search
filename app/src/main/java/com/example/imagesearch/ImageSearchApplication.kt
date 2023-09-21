package com.example.imagesearch

import android.app.Application
import com.example.imagesearch.database.AppDatabase

class ImageSearchApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}