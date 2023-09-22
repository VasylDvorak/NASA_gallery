package com.nasa_gallery.domain.application

import com.nasa_gallery.data.net.model.PictureOfTheDayResponseData


sealed class AnswerFromServerStatePictureOfTheDay {
    data class Success(val serverResponseData: PictureOfTheDayResponseData) : AnswerFromServerStatePictureOfTheDay()
    data class Error(val error: Throwable) : AnswerFromServerStatePictureOfTheDay()
    data class Loading(val progress: Int?) : AnswerFromServerStatePictureOfTheDay()
}
