package com.nasa_gallery.ui.view.recycler.from_seminar

const val TYPE_EARTH = 0
const val TYPE_MARS = 1
const val TYPE_HEADER = 2
data class Data(
    var id:Int =0,
    var type: Int = TYPE_MARS,
    var priority:Int =0,
    var name: String = "Text",
    var someDescription: String? = "Description"

)
