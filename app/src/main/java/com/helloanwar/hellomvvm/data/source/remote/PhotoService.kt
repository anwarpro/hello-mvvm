package com.helloanwar.hellomvvm.data.source.remote

import com.helloanwar.hellomvvm.data.model.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {
    @GET("photos")
    suspend fun getPhotos(): Response<PhotoResponse>
}