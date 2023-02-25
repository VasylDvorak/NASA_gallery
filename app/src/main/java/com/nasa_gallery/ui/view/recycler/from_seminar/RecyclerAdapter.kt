package com.nasa_gallery.ui.view.recycler.from_seminar

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.R
import com.nasa_gallery.databinding.*
import com.nasa_gallery.ui.view.recycler.from_seminar.diffutil.Change
import com.nasa_gallery.ui.view.recycler.from_seminar.diffutil.DiffUtilCallback
import com.nasa_gallery.ui.view.recycler.from_seminar.diffutil.createCombinedPayload

class RecyclerAdapter(
   private var listData: MutableList<Pair<Data, Boolean>>, val callbackAddMars: AddItem,
   val callbackAddEarth: AddItem, val callbackRemove: RemoveItem
) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setListDataForDiffUtil(listDataNew: MutableList<Pair<Data, Boolean>>){
     val diff = DiffUtil.calculateDiff(DiffUtilCallback(listData, listDataNew))
        diff.dispatchUpdatesTo(this)
    }

    fun setListDataRemove(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int){
        listData = listDataNew
        notifyItemRemoved(position)
        listData = listDataNew // Обработать во viewModel
    }
    fun setListDataAdd(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int){
        listData = listDataNew
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val parentContext = parent.context
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parentContext))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding =
                    FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parentContext))
                MarsViewHolder(binding)
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
        if(payloads.isEmpty()){
        super.onBindViewHolder(holder, position, payloads)
    }else{
        val createCombinedPayload = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
           if( createCombinedPayload.newData.first.name != createCombinedPayload.oldData.first.name)
holder.itemView.findViewById<TextView>(R.id.name).text = createCombinedPayload.newData.first.name
    }

    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }



   inner class MarsViewHolder(
       val binding: FragmentRecyclerItemMarsBinding,
   ) :
        BaseViewHolder(binding.root){
        override fun bind(data: Pair<Data, Boolean>) {
            binding.apply{
                name.text = data.first.name
            addItemImageView.setOnClickListener{
                callbackAddMars.add(layoutPosition)
            }

            removeItemImageView.setOnClickListener{
                callbackRemove.remove(layoutPosition)
            }

            moveItemUp.setOnClickListener {
           // пепеместить во View Model repository

                        if (layoutPosition >1){
listData.removeAt(layoutPosition).apply {
    listData.add(layoutPosition -1, this)
}
            notifyItemMoved(layoutPosition, layoutPosition -1)

            }}

                moveItemDown.setOnClickListener {
                    // пепеместить во View Model repository
                       if (listData.size >(layoutPosition+1)){
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition +1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition +1)

                }}


            if (listData[layoutPosition].second)
            {   editText.visibility = View.VISIBLE
                editNote(binding.marsDescriptionTextView, binding.apply, layoutPosition)
            } else {editText.visibility = View.GONE}



            marsImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let{
                    it.first to !it.second
                }
notifyItemChanged(layoutPosition)
            }
        }}

       override fun onItemSelected() {

           itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color3))
       }

       override fun onItemClear() {
           itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,
               R.color.white
           ))
       }

   }


    private fun editNote(
        marsDescriptionTextView: AppCompatEditText,
        apply: ImageView,
        layoutPosition: Int,
    ) {

            var newNote =""

                marsDescriptionTextView.apply {
                    setText(listData[layoutPosition].first.someDescription)
                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            newNote = charSequence.toString()
                        }
                        override fun afterTextChanged(editable: Editable) {} })
                    apply.setOnClickListener {
                        listData[layoutPosition].first.someDescription=newNote
                      //  viewModel.updateNote(docsDataBase)
                    }

                }


    }

  inner class EarthViewHolder(
        val binding: FragmentRecyclerItemEarthBinding,
    ) :
        BaseViewHolder(binding.root){
        override fun bind(data: Pair<Data, Boolean>) {
            binding.apply{
                name.text = data.first.name
                addItemImageView.setOnClickListener{
                    callbackAddEarth.add(layoutPosition)
                }

                removeItemImageView.setOnClickListener{
                    callbackRemove.remove(layoutPosition)
                }

                moveItemUp.setOnClickListener {
                    // пепеместить во View Model repository

                    if (layoutPosition >1){
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition -1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition -1)

                    }}

                moveItemDown.setOnClickListener {
                    // пепеместить во View Model repository
                    if (listData.size >(layoutPosition+1)){
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition +1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition +1)

                    }}


                if (listData[layoutPosition].second)
                {   editText.visibility = View.VISIBLE
                    editNote(binding.marsDescriptionTextView, binding.apply, layoutPosition)
                } else {editText.visibility = View.GONE}



                marsImageView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let{
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
            }}

        override fun onItemSelected() {

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color3))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,
                R.color.white
            ))
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
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,
                R.color.white
            ))
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
}


