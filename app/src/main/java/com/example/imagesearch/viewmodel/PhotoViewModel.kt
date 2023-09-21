package com.example.imagesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.model.Photo
import com.example.imagesearch.network.PhotoApi
import kotlinx.coroutines.launch

enum class LayoutType { LIST, GRID }
enum class ApiStatus { INITIAL, LOADING, ERROR, DONE, EMPTY }

class PhotoViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()

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

class PhotoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}