package com.example.imagesearch.database.SearchHistory

import kotlinx.coroutines.flow.Flow

class SearchHistoryRepository(private val searchHistoryDao: SearchHistoryDao) {
    val dataFlow: Flow<List<SearchHistory>> = searchHistoryDao.getAll()

    suspend fun upsert(searchHistory: SearchHistory) {
        val id = searchHistoryDao.getIdByQueryText(searchHistory.queryText)
        if (id != null) {
            searchHistory.id = id
        }
        searchHistoryDao.upsertSearchHistory(searchHistory)
    }
}