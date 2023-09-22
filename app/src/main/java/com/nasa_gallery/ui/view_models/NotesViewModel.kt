package com.nasa_gallery.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa_gallery.data.notes.model_notes.DataForNotes
import com.nasa_gallery.data.notes.repository_notes.RepositoryNotesImpl

class NotesViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MutableList<Pair<DataForNotes, Boolean>>>
    = MutableLiveData(),
    private val dataImpl: RepositoryNotesImpl = RepositoryNotesImpl()
) :
    ViewModel() {
    fun getDataForNotesForDiffUtil(instanceNumber: Boolean):
            LiveData<MutableList<Pair<DataForNotes, Boolean>>> {
        liveDataForViewToObserve.postValue(
            dataImpl.getDataForNotesDiffUtil(instanceNumber)
        )
        return liveDataForViewToObserve
    }

    fun getDataForNotes(): LiveData<MutableList<Pair<DataForNotes, Boolean>>> {
        liveDataForViewToObserve.postValue(
            dataImpl.getDataForNotes()
        )
        return liveDataForViewToObserve
    }

    fun addEarth(data: MutableList<Pair<DataForNotes, Boolean>>, position: Int):
            LiveData<MutableList<Pair<DataForNotes, Boolean>>> {
        liveDataForViewToObserve.postValue(
            dataImpl.addEarth(data, position)
        )
        return liveDataForViewToObserve
    }

    fun addMars(data: MutableList<Pair<DataForNotes, Boolean>>, position: Int):
            LiveData<MutableList<Pair<DataForNotes, Boolean>>> {
        liveDataForViewToObserve.postValue(
            dataImpl.addMars(data, position)
        )
        return liveDataForViewToObserve
    }

    fun remove(data: MutableList<Pair<DataForNotes, Boolean>>, position: Int):
            LiveData<MutableList<Pair<DataForNotes, Boolean>>> {
        liveDataForViewToObserve.postValue(
            dataImpl.remove(data, position)
        )
        return liveDataForViewToObserve
    }


}
