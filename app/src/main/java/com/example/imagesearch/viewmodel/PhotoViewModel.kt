package com.example.imagesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.model.Photo
import com.example.imagesearch.network.PhotoApi
import kotlinx.coroutines.launch

enum class LayoutType { LIST, GRID }
enum class ApiStatus { INITIAL, LOADING, ERROR, DONE, EMPTY }

class PhotoViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos
    val searchText = MutableLiveData("")
    val layoutType = MutableLiveData(LayoutType.LIST)

    init {
        _status.value = ApiStatus.INITIAL
    }

    fun getPhotoItems() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
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

    fun toggleView() {
        if (layoutType.value === LayoutType.GRID) {
            layoutType.setValue(LayoutType.LIST)
        } else {
            layoutType.setValue(LayoutType.GRID)
        }
    }
}