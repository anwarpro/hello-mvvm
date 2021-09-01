package com.helloanwar.hellomvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.helloanwar.hellomvvm.data.PhotoRepository
import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.utils.isOnline
import kotlinx.coroutines.launch

class MainViewModel(
    private val photoRepository: PhotoRepository,
    application: Application? = null
) : AndroidViewModel(application!!) {

    private val _photos = MutableLiveData<PhotoResponse>()
    val photos: LiveData<PhotoResponse>
        get() = _photos

    fun getPhotos() = viewModelScope.launch {
        _photos.value = photoRepository.getPhotos(
            getApplication<Application>().applicationContext.isOnline()
        )
    }

    init {
        getPhotos()
    }
}