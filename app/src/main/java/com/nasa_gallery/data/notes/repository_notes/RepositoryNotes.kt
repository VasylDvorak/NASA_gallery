package com.nasa_gallery.data.notes.repository_notes

import com.nasa_gallery.data.notes.model_notes.DataForNotes

interface RepositoryNotes {
    fun getDataForNotes() : MutableList<Pair<DataForNotes, Boolean>>
    fun getDataForNotesDiffUtil(instanceNumber: Boolean): MutableList<Pair<DataForNotes, Boolean>>
}