package com.example.imagesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.database.AppDatabase
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.database.SearchHistory.SearchHistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val searchHistoryDao: SearchHistoryDao
    private val _searchHistories = MutableLiveData<List<SearchHistory>>()
    val searchHistories: LiveData<List<SearchHistory>> = _searchHistories

    init {
        searchHistoryDao = AppDatabase.getDatabase(application).searchHistoryDao()
        searchHistoryDao.getAll().observeForever {
            _searchHistories.value = it
        }
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