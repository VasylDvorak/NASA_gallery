package com.nasa_gallery.ui.view.recycler.from_lesson

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)
}
