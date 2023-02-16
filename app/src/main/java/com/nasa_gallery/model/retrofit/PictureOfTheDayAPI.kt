package com.nasa_gallery.model.retrofit

import com.nasa_gallery.model.PictureOfTheDayResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDayByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
    ): Call<PictureOfTheDayResponseData>
}
