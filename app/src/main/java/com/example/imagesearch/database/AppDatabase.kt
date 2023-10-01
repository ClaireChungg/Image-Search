package com.example.imagesearch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.database.SearchHistory.SearchHistoryDao

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}