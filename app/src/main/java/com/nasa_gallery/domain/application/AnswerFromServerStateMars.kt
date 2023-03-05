package com.nasa_gallery.domain.application

import com.nasa_gallery.data.net.model.mars_data.MarsPhotosData


sealed class AnswerFromServerStateMars {
    data class Success(val serverResponseData: MarsPhotosData) : AnswerFromServerStateMars()
    data class Error(val error: Throwable) : AnswerFromServerStateMars()
    data class Loading(val progress: Int?) : AnswerFromServerStateMars()
}
