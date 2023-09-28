package com.example.imagesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.database.SearchHistory.SearchHistory
import com.example.imagesearch.database.SearchHistory.SearchHistoryDao
import com.example.imagesearch.database.SearchHistory.SearchHistoryRepository
import com.example.imagesearch.model.Photo
import com.example.imagesearch.network.PhotoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

enum class LayoutType { LIST, GRID }
enum class ApiStatus { INITIAL, LOADING, ERROR, DONE, EMPTY }

class SearchViewModel(searchHistoryDao: SearchHistoryDao) : ViewModel() {
    private val _status = MutableLiveData(ApiStatus.INITIAL)
    val status: LiveData<ApiStatus> = _status
    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos
    val searchText = MutableLiveData("")
    val layoutType = MutableLiveData(LayoutType.LIST)

    private val repository: SearchHistoryRepository = SearchHistoryRepository(searchHistoryDao)
    val searchHistories: Flow<List<SearchHistory>> = repository.dataFlow

    fun performSearch(queryText: String) {
        searchText.value = queryText
        viewModelScope.launch(Dispatchers.IO) { repository.upsert(SearchHistory(0, queryText)) }
        getPhotoItems()
    }

    fun toggleView() {
        if (layoutType.value === LayoutType.GRID) {
            layoutType.setValue(LayoutType.LIST)
        } else {
            layoutType.setValue(LayoutType.GRID)
        }
    }

    private fun getPhotoItems() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _photos.value = listOf()
            try {
                _photos.value =
                    PhotoApi.retrofitService.getPhotos(
                        "39437674-aad5c98d5efc56c2a1b3cbcc2",
                        searchText.value ?: ""
                    ).hits
                _status.value =
                    if (_photos.value!!.isNotEmpty()) ApiStatus.DONE else ApiStatus.EMPTY
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}

class SearchViewModelFactory(private val searchHistoryDao: SearchHistoryDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchHistoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}