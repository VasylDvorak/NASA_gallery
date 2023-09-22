package com.nasa_gallery.data.notes.repository_notes

import com.nasa_gallery.data.notes.model_notes.DataForNotes

interface RepositoryNotes {
    fun getDataForNotes(): MutableList<Pair<DataForNotes, Boolean>>

    fun getDataForNotesDiffUtil(instanceNumber: Boolean): MutableList<Pair<DataForNotes, Boolean>>

    fun remove(data: MutableList<Pair<DataForNotes, Boolean>>, position: Int
    ): MutableList<Pair<DataForNotes, Boolean>>

    fun addMars(data: MutableList<Pair<DataForNotes, Boolean>>, poisition: Int
    ): MutableList<Pair<DataForNotes, Boolean>>

    fun addEarth(data: MutableList<Pair<DataForNotes, Boolean>>, poisition: Int
    ): MutableList<Pair<DataForNotes, Boolean>>
}