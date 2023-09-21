package com.example.imagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.database.AppDatabase
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.database.SearchHistory.SearchHistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val searchHistoryDao: SearchHistoryDao
    fun getSearchHistories(): Flow<List<SearchHistory>> = searchHistoryDao.getAll()


    init {
        searchHistoryDao = AppDatabase.getDatabase(application).searchHistoryDao()
    }

    fun insert(searchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) { searchHistoryDao.addSearchHistory(searchHistory) }
    }
}

//class SearchHistoryViewModelFactory(private val searchHistoryDao: SearchHistoryDao) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SearchHistoryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SearchHistoryViewModel(searchHistoryDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}