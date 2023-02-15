package com.nasa_gallery.model.app

import com.nasa_gallery.model.PictureOfTheDayResponseData


sealed class AppState {
    data class Success(val serverResponseData: PictureOfTheDayResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
