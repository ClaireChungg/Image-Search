package com.example.imagesearch.database.SearchHistory

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Upsert
    suspend fun upsertSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM searchHistory ORDER BY update_time DESC")
    fun getAll(): Flow<List<SearchHistory>>

    @Query("SELECT id FROM searchHistory WHERE query_text = :queryText")
    fun getIdByQueryText(queryText: String): Int?
}