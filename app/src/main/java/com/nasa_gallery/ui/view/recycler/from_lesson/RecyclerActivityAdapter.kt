package com.nasa_gallery.ui.view.recycler.from_lesson

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.R
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_EARTH
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_MARS

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var listData: MutableList<Pair<Data, Boolean>>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EARTH -> EarthViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_earth, parent, false) as View
            )
            TYPE_MARS ->
                MarsViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_mars, parent, false) as View
                )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    fun appendItem() {
        listData.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data(TYPE_MARS, "Mars", ""), false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.descriptionTextView).text = data.first.someDescription
                itemView.findViewById<ImageView>(R.id.wikiImageView).setOnClickListener { onListItemClickListener.onItemClick(data.first) }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        override fun bind(data: Pair<Data, Boolean>) {
            itemView.findViewById<ImageView>(R.id.marsImageView).setOnClickListener { onListItemClickListener.onItemClick(data.first) }
            itemView.findViewById<ImageView>(R.id.addItemImageView).setOnClickListener { addItem() }
            itemView.findViewById<ImageView>(R.id.removeItemImageView).setOnClickListener { removeItem() }
            itemView.findViewById<ImageView>(R.id.moveItemDown).setOnClickListener { moveDown() }
            itemView.findViewById<ImageView>(R.id.moveItemUp).setOnClickListener { moveUp() }
            itemView.findViewById<TextView>(R.id.marsDescriptionTextView).visibility =
                if (data.second) View.VISIBLE else View.GONE
            itemView.findViewById<TextView>(R.id.marsTextView).setOnClickListener { toggleText() }
            itemView.findViewById<ImageView>(R.id.dragHandleImageView).setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }

        }

        private fun toggleText() {
            listData[layoutPosition] = listData[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                listData.removeAt(currentPosition).apply {
                    listData.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < listData.size - 1 }?.also { currentPosition ->
                listData.removeAt(currentPosition).apply {
                    listData.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun addItem() {
            listData.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            listData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
        }
    }
}