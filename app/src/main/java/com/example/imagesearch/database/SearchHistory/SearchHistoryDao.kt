package com.example.imagesearch.database.SearchHistory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun addSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM searchHistory ORDER BY id DESC")
    fun getAll(): LiveData<List<SearchHistory>>
}