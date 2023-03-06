package com.nasa_gallery.data.net.repository_nasa

interface Repository {
   fun getRetrofitImpl(): PictureOfTheDayAPI
   fun getMarsPhotos(): PictureOfTheDayAPI
}