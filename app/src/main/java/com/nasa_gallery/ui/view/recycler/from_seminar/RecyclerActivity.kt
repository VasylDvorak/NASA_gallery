package com.nasa_gallery.ui.view.recycler.from_seminar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.nasa_gallery.databinding.ActivityRecyclerBinding
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_HEADER
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_MARS

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding

    val data = arrayListOf(
        Pair(Data(0, TYPE_HEADER, "Заголовок"), false),
        Pair(Data(1, TYPE_EARTH, "Earth"), false),
        Pair(Data(2, TYPE_EARTH, "Earth"), false),
        Pair(Data(3, TYPE_MARS, "Mars"), false),
        Pair(Data(4, TYPE_EARTH, "Earth"), false),
        Pair(Data(5, TYPE_EARTH, "Earth"), false),
        Pair(Data(6, TYPE_MARS, "Mars"), false)
    )
    private var isNewList = false
    lateinit var adapter: RecyclerAdapter

    private fun changeAdapterData () {
        adapter.setListDataForDiffUtil(createItemList(isNewList).map { it }.toMutableList())
        isNewList = !isNewList
    }



    private fun createItemList (instanceNumber: Boolean ): List<Pair<Data, Boolean >> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data( 0, TYPE_HEADER, "Header" ), false ),
                Pair(Data( 1, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 2, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 3, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 4, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 5, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 6, TYPE_MARS, "Mars" , "" ), false )
            )
            true -> listOf(
                Pair(Data( 0, TYPE_HEADER, "Header" ), false ),
                Pair(Data( 1, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 2, TYPE_MARS, "Jupiter" , "" ), false ),
                Pair(Data( 3, TYPE_MARS, "Mars" , "" ), false ),
                Pair(Data( 4, TYPE_MARS, "Neptune" , "" ), false ),
                Pair(Data( 5, TYPE_MARS, "Saturn" , "" ), false ),
                Pair(Data( 6, TYPE_MARS, "Mars" , "" ), false )
            )
        }}







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter

ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        binding.recyclerActivityFAB.setOnClickListener{
            changeAdapterData()
        }
    }









    val callbackAdd = AddItem {
// обработать в репозитории через ViewModel
        data.add(it, Pair(Data(0, TYPE_MARS, "Mars NEW"), false))
        adapter.setListDataAdd(data, it)
    }
    val callbackRemove = RemoveItem {
// обработать в репозитории через ViewModel
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }
}