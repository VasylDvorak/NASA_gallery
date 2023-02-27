package com.nasa_gallery.data.net

interface Repository {
   fun getRetrofitImpl(): PictureOfTheDayAPI
   fun getMarsPhotos(): PictureOfTheDayAPI
}