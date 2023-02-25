package com.nasa_gallery.ui.view.recycler.from_seminar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.nasa_gallery.databinding.FragmentRecyclerBinding
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_HEADER
import com.nasa_gallery.ui.view.recycler.from_lesson.Data.Companion.TYPE_MARS

class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!


    val data = arrayListOf(
        Pair(Data(0, TYPE_HEADER, 0,"Заголовок"), false),
        Pair(Data(1, TYPE_EARTH, 1,"Earth"), false),
        Pair(Data(2, TYPE_EARTH, 2, "Earth"), false),
        Pair(Data(3, TYPE_MARS, 3,"Mars"), false),
        Pair(Data(4, TYPE_EARTH, 4,"Earth"), false),
        Pair(Data(5, TYPE_EARTH, 5,"Earth"), false),
        Pair(Data(6, TYPE_MARS, 6,"Mars"), false)
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
                Pair(Data( 0, TYPE_HEADER, 0,"Header" ), false ),
                Pair(Data( 1, TYPE_MARS, 1,"Mars" , "" ), false ),
                Pair(Data( 2, TYPE_MARS, 2,"Mars" , "" ), false ),
                Pair(Data( 3, TYPE_MARS, 3,"Mars" , "" ), false ),
                Pair(Data( 4, TYPE_MARS, 4,"Mars" , "" ), false ),
                Pair(Data( 5, TYPE_MARS, 5,"Mars" , "" ), false ),
                Pair(Data( 6, TYPE_MARS, 6,"Mars" , "" ), false )
            )
            true -> listOf(
                Pair(Data( 0, TYPE_HEADER, 0,"Header" ), false ),
                Pair(Data( 1, TYPE_MARS, 1,"Mars" , "" ), false ),
                Pair(Data( 2, TYPE_MARS, 2,"Jupiter" , "" ), false ),
                Pair(Data( 3, TYPE_MARS, 3,"Mars" , "" ), false ),
                Pair(Data( 4, TYPE_MARS, 4,"Neptune" , "" ), false ),
                Pair(Data( 5, TYPE_MARS, 5,"Saturn" , "" ), false ),
                Pair(Data( 6, TYPE_MARS, 6,"Mars" , "" ), false )
            )
        }}



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        adapter = RecyclerAdapter(data, callbackAddMars, callbackAddEarth,  callbackRemove)
        binding.recyclerView.adapter = adapter

ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)

        binding.compareFAB.setOnClickListener{
            changeAdapterData()
        }

    }









    val callbackAddMars = AddItem {
// обработать в репозитории через ViewModel
        data.add(it, Pair(Data(0, TYPE_MARS, 0,"Mars NEW"), false))
        adapter.setListDataAdd(data, it)
    }

    val callbackAddEarth = AddItem {
// обработать в репозитории через ViewModel
        data.add(it, Pair(Data(0, TYPE_EARTH,0, "Earth NEW"), false))
        adapter.setListDataAdd(data, it)
    }
    val callbackRemove = RemoveItem {
// обработать в репозитории через ViewModel
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}