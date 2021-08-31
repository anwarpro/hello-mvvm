package com.helloanwar.hellomvvm.data.source.remote

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PhotoRemoteSource {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun getPhotos(): Response<PhotoResponse> {
        val service = retrofit.create(PhotoService::class.java)
        return service.getPhotos()
    }
}