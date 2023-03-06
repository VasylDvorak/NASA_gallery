package com.nasa_gallery.data.notes.model_notes

import java.text.SimpleDateFormat
import java.util.*

data class PairDataForNotes(
    var pairDataForNotes: Pair<DataForNotes, Boolean>
    = Pair(DataForNotes(0, TYPE_HEADER, 0, "Заголовок"), false)
) {
    fun getDataForNotesPair(): MutableList<Pair<DataForNotes, Boolean>> = arrayListOf(
        Pair(DataForNotes(0, TYPE_HEADER, 0, "Заголовок"), false),
        Pair(
            DataForNotes(1, TYPE_REMIND, getRandom(), "Заметка напоминание 1",
            getData()), false),
        Pair(
            DataForNotes(2, TYPE_REMIND, getRandom(), "Заметка напоминание 2",
            getData()), false),
        Pair(
            DataForNotes(3, TYPE_SIMPLE, getRandom(), "Заметка простая 1",
            getData()), false),
        Pair(
            DataForNotes(4, TYPE_REMIND, getRandom(), "Заметка напоминание 3",
            getData()), false),
        Pair(
            DataForNotes(5, TYPE_REMIND, getRandom(), "Заметка напоминание 4",
            getData()), false),
        Pair(
            DataForNotes(6, TYPE_SIMPLE, getRandom(), "Заметка простая 2",
            getData()), false)
    )

    fun createItemList(instanceNumber: Boolean): MutableList<Pair<DataForNotes, Boolean>> {
        return when (instanceNumber) {
            false -> mutableListOf(
                Pair(DataForNotes(0, TYPE_HEADER, 0, "Заголовок"), false),
                Pair(
                    DataForNotes(1, TYPE_SIMPLE, getRandom(), "Заметка простая 1",
                    getData()), false),
                Pair(
                    DataForNotes(2, TYPE_SIMPLE, getRandom(), "Заметка простая 2",
                    getData()), false),
                Pair(
                    DataForNotes(3, TYPE_SIMPLE, getRandom(), "Заметка простая 3",
                    getData()), false),
                Pair(
                    DataForNotes(4, TYPE_SIMPLE, getRandom(), "Заметка простая 4",
                    getData()), false),
                Pair(
                    DataForNotes(5, TYPE_SIMPLE, getRandom(), "Заметка простая 5",
                    getData()), false),
                Pair(
                    DataForNotes(6, TYPE_SIMPLE, getRandom(), "Заметка простая 6",
                    getData()), false))
            true -> mutableListOf(
                Pair(DataForNotes(0, TYPE_HEADER, 0, "Заголовок"), false),
                Pair(
                    DataForNotes(1, TYPE_REMIND, getRandom(), "Заметка напоминание 7",
                        getData()), false),
                Pair(
                    DataForNotes(2, TYPE_REMIND, getRandom(), "Заметка напоминание 8",
                        getData()), false),
                Pair(
                    DataForNotes(3, TYPE_REMIND, getRandom(), "Заметка напоминание 3",
                        getData()), false),
                Pair(
                    DataForNotes(4, TYPE_REMIND, getRandom(), "Заметка напоминание 4",
                    getData()), false),
                Pair(
                    DataForNotes(5, TYPE_REMIND, getRandom(), "Заметка напоминание 9",
                    getData()), false),
                Pair(
                    DataForNotes(6, TYPE_REMIND, getRandom(), "Заметка напоминание 10",
                    getData()), false))
        }
    }

    private fun getRandom() = (1..100).random()
    private fun getData(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, EEE, HH:mm:ss z")
        val date = dateFormat.format(Date())
        return date
    }
}