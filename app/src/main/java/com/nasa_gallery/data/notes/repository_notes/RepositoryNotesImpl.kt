package com.nasa_gallery.data.notes.repository_notes

import com.nasa_gallery.data.notes.model_notes.DataForNotes
import com.nasa_gallery.data.notes.model_notes.PairDataForNotes
import com.nasa_gallery.data.notes.model_notes.TYPE_REMIND
import com.nasa_gallery.data.notes.model_notes.TYPE_SIMPLE
import java.text.SimpleDateFormat
import java.util.*

class RepositoryNotesImpl : RepositoryNotes {
    private var pairDataForNotes: MutableList<Pair<DataForNotes, Boolean>> =
        PairDataForNotes().getDataForNotesPair()

    override fun getDataForNotes(): MutableList<Pair<DataForNotes, Boolean>> = pairDataForNotes

    override fun getDataForNotesDiffUtil(instanceNumber: Boolean): MutableList<Pair<DataForNotes,
            Boolean>> = PairDataForNotes().createItemList(instanceNumber)

    override fun addEarth(
        data: MutableList<Pair<DataForNotes, Boolean>>, poisition: Int
    ): MutableList<Pair<DataForNotes, Boolean>> {
        val number = (data.size + 1).toString()
        data.add(
            poisition, Pair(
                DataForNotes(
                    number.toInt(), TYPE_REMIND, getRandom(),
                    "Заметка напоминание ${number}", getData()
                ), false
            )
        )
        pairDataForNotes = data
        return data
    }

    override fun addMars(
        data: MutableList<Pair<DataForNotes, Boolean>>, poisition: Int
    ): MutableList<Pair<DataForNotes, Boolean>> {
        val number = (data.size + 1).toString()
        data.add(
            poisition, Pair(
                DataForNotes(
                    number.toInt(), TYPE_SIMPLE, getRandom(),
                    "Заметка простая ${number}", getData()
                ), false
            )
        )
        pairDataForNotes = data
        return data
    }

    override fun remove(
        data: MutableList<Pair<DataForNotes, Boolean>>, position: Int
    ): MutableList<Pair<DataForNotes, Boolean>> {
        data.removeAt(position)
        pairDataForNotes = data
        return data
    }

    private fun getData(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, EEE, HH:mm:ss z")
        val date = dateFormat.format(Date())
        return date
    }

    private fun getRandom() = (1..100).random()

}