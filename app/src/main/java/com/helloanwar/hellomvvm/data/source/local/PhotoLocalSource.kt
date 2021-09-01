package com.helloanwar.hellomvvm.data.source.local

import com.github.ajalt.timberkt.Timber.e
import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem
import com.helloanwar.hellomvvm.data.source.PhotoSource

class PhotoLocalSource(
    private val database: HelloMVVMDatabase? = null
) : PhotoSource {
    override suspend fun getPhotos(): PhotoResponse {
        val photos = database?.photoDao()?.getAll()
        return PhotoResponse().also {
            photos?.let { _photos ->
                it.addAll(_photos)
            }
        }
    }

    override suspend fun save(photoResponseItem: PhotoResponseItem): Boolean {
        return try {
            database?.photoDao()?.insertAll(photoResponseItem)
            true
        } catch (e: Exception) {
            e(e)
            false
        }
    }
}