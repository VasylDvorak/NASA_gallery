package com.nasa_gallery.ui.view.recycler.from_seminar.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.nasa_gallery.ui.view.recycler.from_seminar.Data

class DiffUtilCallback(
   private val oldItems: List<Pair<Data, Boolean>>,
   private val newItems: List<Pair<Data, Boolean>>



): DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size


    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return  oldItems [oldItemPosition].first.id == newItems [oldItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name

    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return Change(old, new)
    }
}

data class Change<T>(val oldData:T, val newData:T)

fun <T> createCombinedPayload(payloads: List <Change<T>>) : Change <T>{
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()
    return Change(firstChange.oldData, lastChange.newData)
}