package com.helloanwar.hellomvvm.data.source

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem

interface PhotoSource {
    suspend fun getPhotos(): PhotoResponse?
    suspend fun save(photoResponseItem: PhotoResponseItem): Boolean
}