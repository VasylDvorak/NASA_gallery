package com.nasa_gallery.ui.view.recycler.from_seminar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.databinding.ActivityRecyclerItemEarthBinding
import com.nasa_gallery.databinding.ActivityRecyclerItemHeaderBinding
import com.nasa_gallery.databinding.ActivityRecyclerItemMarsBinding

class RecyclerAdapter(
    private var listData: MutableList<Data>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(listData[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(listData[position])
            }
            else -> {
                (holder as HeaderViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    class EarthViewHolder(val binding: ActivityRecyclerItemEarthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class MarsViewHolder(val binding: ActivityRecyclerItemMarsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.name.text = data.name
        }

    }
}

class HeaderViewHolder(val binding: ActivityRecyclerItemHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Data) {
        binding.name.text = data.name
    }
}


