package com.nasa_gallery.data.retrofit

interface Repository {
   fun getRetrofitImpl(): PictureOfTheDayAPI
   fun getMarsPhotos(): PictureOfTheDayAPI
}