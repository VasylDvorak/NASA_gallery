package com.nasa_gallery.domain.Application

import com.nasa_gallery.domain.entity.model.mars_data.MarsPhotosData


sealed class AppStateMars {
    data class Success(val serverResponseData: MarsPhotosData) : AppStateMars()
    data class Error(val error: Throwable) : AppStateMars()
    data class Loading(val progress: Int?) : AppStateMars()
}
