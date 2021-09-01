package com.helloanwar.hellomvvm.data

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.source.local.PhotoLocalSource
import com.helloanwar.hellomvvm.data.source.remote.PhotoRemoteSource

class PhotoRepository(
    private val localSource: PhotoLocalSource,
    private val remoteSource: PhotoRemoteSource
) {
    suspend fun getPhotos(forceLoad: Boolean = true): PhotoResponse? {

        if (forceLoad) {
            return remoteSource.getPhotos().also {
                it?.forEach {
                    localSource.save(it)
                }
            }
        }

        return localSource.getPhotos()
    }
}