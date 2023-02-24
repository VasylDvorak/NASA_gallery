package com.nasa_gallery.ui.view.recycler.from_seminar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.databinding.ActivityRecyclerItemEarthBinding
import com.nasa_gallery.databinding.ActivityRecyclerItemHeaderBinding
import com.nasa_gallery.databinding.ActivityRecyclerItemMarsBinding

class RecyclerAdapter(
   private var listData: MutableList<Data>, val callbackAdd: AddItem, val callbackRemove: RemoveItem
) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataRemove(listDataNew: MutableList<Data>, position: Int){
        listData = listDataNew
        notifyItemRemoved(position)
    }
    fun setListDataAdd(listDataNew: MutableList<Data>, position: Int){
        listData = listDataNew
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    ActivityRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding =
                    ActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding =
                    ActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }

    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }



   inner class MarsViewHolder(val binding: ActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
            binding.addItemImageView.setOnClickListener{
                callbackAdd.add(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener{
                callbackRemove.remove(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener {
           // пепеместить во View Model repository

                        if (layoutPosition >1){
listData.removeAt(layoutPosition).apply {
    listData.add(layoutPosition -1, this)
}
            notifyItemMoved(layoutPosition, layoutPosition -1)

            }}

                binding.moveItemDown.setOnClickListener {
                    // пепеместить во View Model repository
                       if (listData.size >(layoutPosition+1)){
                    listData.removeAt(layoutPosition).apply {
                        listData.add(layoutPosition +1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition +1)

                }}
        }

    }

    class EarthViewHolder(val binding: ActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Data)
    }}


