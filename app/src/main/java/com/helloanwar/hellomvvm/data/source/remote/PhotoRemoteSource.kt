package com.helloanwar.hellomvvm.data.source.remote

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import com.helloanwar.hellomvvm.data.model.PhotoResponseItem
import com.helloanwar.hellomvvm.data.source.PhotoSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PhotoRemoteSource : PhotoSource {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override suspend fun getPhotos(): PhotoResponse? {
        val service = retrofit.create(PhotoService::class.java)
        return service.getPhotos().body()
    }

    override suspend fun save(photoResponseItem: PhotoResponseItem): Boolean {
        TODO("Not yet implemented")
    }
}