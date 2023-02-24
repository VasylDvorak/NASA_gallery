package com.nasa_gallery.ui.view.recycler.from_seminar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nasa_gallery.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding

    val data = arrayListOf(
        Pair(Data(TYPE_HEADER, "Заголовок"), false),
        Pair(Data(TYPE_EARTH, "Earth"), false),
        Pair(Data(TYPE_EARTH, "Earth"), false),
        Pair(Data(TYPE_MARS, "Mars"), false),
        Pair(Data(TYPE_EARTH, "Earth"), false),
        Pair(Data(TYPE_EARTH, "Earth"), false),
        Pair(Data(TYPE_MARS, "Mars"), false)
    )
    lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter


    }

    val callbackAdd = AddItem {
// обработать в репозитории через ViewModel
        data.add(it, Pair(Data(TYPE_MARS, "Mars NEW"), false))
        adapter.setListDataAdd(data, it)
    }
    val callbackRemove = RemoveItem {
// обработать в репозитории через ViewModel
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }
}