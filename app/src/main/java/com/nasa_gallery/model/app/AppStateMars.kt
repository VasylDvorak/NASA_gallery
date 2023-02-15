package com.nasa_gallery.model.app

import com.nasa_gallery.model.mars_data.MarsPhotosData


sealed class AppStateMars {
    data class Success(val serverResponseData: MarsPhotosData) : AppStateMars()
    data class Error(val error: Throwable) : AppStateMars()
    data class Loading(val progress: Int?) : AppStateMars()
}
