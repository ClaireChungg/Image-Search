package com.example.imagesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.database.SearchHistory.SearchHistoryDao
import com.example.imagesearch.database.SearchHistory.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchHistoryViewModel(searchHistoryDao: SearchHistoryDao) : ViewModel() {

    private val repository: SearchHistoryRepository
    val searchHistories: Flow<List<SearchHistory>>

    init {
        repository =
            SearchHistoryRepository(searchHistoryDao)
        searchHistories = repository.getAllData
    }

    fun insert(searchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) { repository.insert(searchHistory) }
    }
}

class SearchHistoryViewModelFactory(private val searchHistoryDao: SearchHistoryDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchHistoryViewModel(searchHistoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}