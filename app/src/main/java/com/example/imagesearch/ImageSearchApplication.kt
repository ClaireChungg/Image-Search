package com.example.imagesearch

import android.app.Application
import androidx.room.Room
import com.example.imagesearch.database.AppDatabase

class ImageSearchApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "search_history_database"
        ).build()
    }
}