package com.helloanwar.hellomvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloanwar.hellomvvm.data.PhotoRepository
import com.helloanwar.hellomvvm.data.model.PhotoResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableLiveData<PhotoResponse>()
    val photos: LiveData<PhotoResponse>
        get() = _photos

    fun getPhotos() = viewModelScope.launch {
        val response = photoRepository.getPhotos()
        if (response.isSuccessful) {
            _photos.value = response.body()
        }
    }

    init {
        getPhotos()
    }
}