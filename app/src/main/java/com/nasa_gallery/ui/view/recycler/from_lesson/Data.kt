package com.nasa_gallery.ui.view.recycler.from_lesson

data class Data(
    val type: Int = TYPE_EARTH,
    val someText: String = "Text",
    val someDescription: String? = "Description"
) {
    companion object {
        const val TYPE_EARTH = 0
        const val TYPE_MARS = 1
        const val TYPE_HEADER = 2
    }
}
