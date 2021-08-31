package com.helloanwar.hellomvvm.data

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.source.remote.PhotoRemoteSource
import retrofit2.Response

class PhotoRepository(
    private val remoteSource: PhotoRemoteSource
) {
    suspend fun getPhotos(): Response<PhotoResponse> {
        return remoteSource.getPhotos()
    }
}