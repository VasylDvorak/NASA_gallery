package com.nasa_gallery.domain.entity.model

const val TYPE_REMIND = 0
const val TYPE_SIMPLE = 1
const val TYPE_HEADER = 2
data class Data(
    var id:Int =0,
    var type: Int = TYPE_REMIND,
    var priority:Int =0,
    var name: String = "Text",
    var dateCreate: String ="",
    var someDescription: String? = ""
)