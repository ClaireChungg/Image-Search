package com.example.imagesearch.database.SearchHistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun addSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM searchHistory ORDER BY id DESC")
    fun getAll(): Flow<List<SearchHistory>>
}