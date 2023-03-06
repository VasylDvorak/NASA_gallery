package com.nasa_gallery.data.notes.repository_notes

import com.nasa_gallery.data.notes.model_notes.DataForNotes
import com.nasa_gallery.data.notes.model_notes.PairDataForNotes

class RepositoryNotesImpl : RepositoryNotes {
    val pairDataForNotes: PairDataForNotes = PairDataForNotes()
    override fun getDataForNotes(): MutableList<Pair<DataForNotes, Boolean>> =
        pairDataForNotes.getDataForNotesPair()

    override fun getDataForNotesDiffUtil(instanceNumber: Boolean): MutableList<Pair<DataForNotes,
            Boolean>> = pairDataForNotes.createItemList(instanceNumber)
}