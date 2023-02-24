package com.nasa_gallery.ui.view.recycler.from_seminar

const val TYPE_EARTH = 0
const val TYPE_MARS = 1
const val TYPE_HEADER = 2
data class Data(
    val type: Int = TYPE_MARS,
    val someText: String = "Text",
    val someDescription: String? = "Description"

)
