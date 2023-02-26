package com.nasa_gallery.ui.view.recycler.notes

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentRecyclerItemHeaderBinding
import com.nasa_gallery.databinding.FragmentRecyclerItemRemindBinding
import com.nasa_gallery.databinding.FragmentRecyclerItemSimpleBinding
import com.nasa_gallery.ui.view.recycler.notes.diffutil.Change
import com.nasa_gallery.ui.view.recycler.notes.diffutil.DiffUtilCallback
import com.nasa_gallery.ui.view.recycler.notes.diffutil.createCombinedPayload


class RecyclerAdapter(
    private var listData: MutableList<Pair<Data, Boolean>>, val callbackAddMars: AddItem,
    val callbackAddEarth: AddItem, val callbackRemove: RemoveItem
) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {


    fun setListDataForDiffUtil(listDataNew: MutableList<Pair<Data, Boolean>>) {
        val diff = DiffUtil.calculateDiff(DiffUtilCallback(listData, listDataNew))
        diff.dispatchUpdatesTo(this)
    }

    fun setFilteredList(filteredList: MutableList<Pair<Data, Boolean>>) {
        for (i in 0..listData.size) {
            notifyItemRemoved(i)
        }
        this.listData = filteredList
        setListDataAdd(listData, listData.size)
    }

    fun setListDataRemove(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataNew
        notifyItemRemoved(position)
        listData = listDataNew
    }

    fun setListDataAdd(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataNew
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val parentContext = parent.context
        return when (viewType) {
            TYPE_REMIND -> {
                val binding =
                    FragmentRecyclerItemRemindBinding.inflate(LayoutInflater.from(parentContext))
                RemindViewHolder(binding)
            }
            TYPE_SIMPLE -> {
                val binding =
                    FragmentRecyclerItemSimpleBinding.inflate(LayoutInflater.from(parentContext))
                SimpleViewHolder(binding)
            }
            else -> {
                val binding =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parentContext))
                HeaderViewHolder(binding)
            }
        }

    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinedPayload =
                createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            if (createCombinedPayload.newData.first.name != createCombinedPayload.oldData.first.name)
                holder.itemView.findViewById<TextView>(R.id.name).text =
                    createCombinedPayload.newData.first.name
        }

    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }


    inner class SimpleViewHolder(
        val binding: FragmentRecyclerItemSimpleBinding,
    ) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.apply {
                name.text =
                    data.first.name + " Приоритет: " + data.first.priority + " Дата " +
                            data.first.dateCreate
                addItemImageView.setOnClickListener {
                    callbackAddMars.add(layoutPosition)
                }

                removeItemImageView.setOnClickListener {
                    callbackRemove.remove(layoutPosition)
                }

                moveItemUp.setOnClickListener {
                    // пепеместить во View Model repository

                    if (layoutPosition > 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)

                    }
                }

                moveItemDown.setOnClickListener {
                    // пепеместить во View Model repository
                    if (listData.size > (layoutPosition + 1)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)

                    }
                }


                if (listData[layoutPosition].second) {
                    editText.visibility = View.VISIBLE
                    editNote(binding.marsDescriptionTextView, binding.apply, layoutPosition)
                } else {
                    editText.visibility = View.GONE
                }



                marsImageView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
            }
        }

        override fun onItemSelected() {

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color3))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

    }


    private fun editNote(
        marsDescriptionTextView: AppCompatEditText,
        apply: ImageView,
        layoutPosition: Int,
    ) {

        var newNote = ""

        marsDescriptionTextView.apply {
            setText(listData[layoutPosition].first.someDescription)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    newNote = charSequence.toString()
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            apply.setOnClickListener {
                listData[layoutPosition].first.someDescription = newNote
                marsDescriptionTextView.hideKeyboard()
                //  viewModel.updateNote(docsDataBase)
            }

        }


    }

    inner class RemindViewHolder(
        val binding: FragmentRecyclerItemRemindBinding,
    ) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.apply {
                name.text =
                    data.first.name + " Приоритет: " + data.first.priority + " Дата " +
                            data.first.dateCreate

                addItemImageView.setOnClickListener {
                    callbackAddEarth.add(layoutPosition)
                }

                removeItemImageView.setOnClickListener {
                    callbackRemove.remove(layoutPosition)
                }

                moveItemUp.setOnClickListener {
                    // пепеместить во View Model repository

                    if (layoutPosition > 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)

                    }
                }

                moveItemDown.setOnClickListener {
                    // пепеместить во View Model repository
                    if (listData.size > (layoutPosition + 1)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)

                    }
                }


                if (listData[layoutPosition].second) {
                    editText.visibility = View.VISIBLE
                    editNote(binding.marsDescriptionTextView, binding.apply, layoutPosition)
                } else {
                    editText.visibility = View.GONE
                }



                marsImageView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
            }
        }

        override fun onItemSelected() {

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color3))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

    }

    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
        }
    }

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        abstract fun bind(data: Pair<Data, Boolean>)
        override fun onItemSelected() {

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color3))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listData.removeAt(fromPosition).apply {
            listData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }

    // Расширяем функционал вью для скрытия клавиатуры
    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }
}


