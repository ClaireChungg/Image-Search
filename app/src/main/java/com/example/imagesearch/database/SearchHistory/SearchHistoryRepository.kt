package com.example.imagesearch.database.SearchHistory

import kotlinx.coroutines.flow.Flow

class SearchHistoryRepository(private val searchHistoryDao: SearchHistoryDao) {
    val getAllData: Flow<List<SearchHistory>> = searchHistoryDao.getAll()

    suspend fun insert(searchHistory: SearchHistory) {
        searchHistoryDao.addSearchHistory(searchHistory)
    }
}