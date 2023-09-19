package com.example.imagesearch.network


import com.example.imagesearch.model.PhotoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://pixabay.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PhotoApiService {
    @GET("api")
    suspend fun getPhotos(
        @Query("key") apiKey: String = "",
        @Query("q") searchString: String,
    ): PhotoResponse
}

object PhotoApi {
    val retrofitService: PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }
}
