package com.nasa_gallery.data.net

import com.nasa_gallery.data.net.model.PictureOfTheDayResponseData
import com.nasa_gallery.data.net.model.mars_data.MarsPhotosData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDayByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
    ): Call<PictureOfTheDayResponseData>

    @GET("mars-photos/api/v1/rovers/{name}/photos")
    fun getMarsPhotos(
        @Path("name") rover: String,
        @Query("earth_date") earth_date: String,
        @Query("camera") camera: String = "fhaz",
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosData>

}
