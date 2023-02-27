package com.nasa_gallery.domain.entity.model.mars_data

data class MarsPhotosData(
    var photos: MutableList<Photo> = mutableListOf()
)