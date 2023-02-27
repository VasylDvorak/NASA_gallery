package com.nasa_gallery.domain.entity.model

import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(
    var copyright: String? = "",
    var date: String? = "",
    var explanation: String? = "",
    var hdurl: String? = "",
    @SerializedName("media_type") var mediaType: String? = "",
    @SerializedName("service_version") var serviceVersion: String? = "",
    var title: String? = "",
    var url: String?,
    )
