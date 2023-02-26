package com.nasa_gallery.ui.view.recycler.notes

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentRecyclerBinding
import java.text.SimpleDateFormat
import java.util.*


class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
companion object{
    var dataSize = 0

}

   val data = arrayListOf(
        Pair(Data(0, TYPE_HEADER, 0, "Заголовок"), false),
        Pair(Data(1, TYPE_REMIND, getRandom(), "Заметка напоминание 1", getData()), false),
        Pair(Data(2, TYPE_REMIND, getRandom(), "Заметка напоминание 2", getData()), false),
        Pair(Data(3, TYPE_SIMPLE, getRandom(), "Заметка простая 1", getData()), false),
        Pair(Data(4, TYPE_REMIND, getRandom(), "Заметка напоминание 3", getData()), false),
        Pair(Data(5, TYPE_REMIND, getRandom(), "Заметка напоминание 4", getData()), false),
        Pair(Data(6, TYPE_SIMPLE, getRandom(), "Заметка простая 2", getData()), false)
    )
    private var isNewList = false
    lateinit var adapter: RecyclerAdapter

    private fun changeAdapterData() {
        adapter.setListDataForDiffUtil(createItemList(isNewList).map { it }.toMutableList())
        isNewList = !isNewList
    }


    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, TYPE_HEADER, 0, "Заголовок"), false),
                Pair(Data(1, TYPE_SIMPLE, getRandom(), "Заметка простая 1", getData()), false),
                Pair(Data(2, TYPE_SIMPLE, getRandom(), "Заметка простая 2", getData()), false),
                Pair(Data(3, TYPE_SIMPLE, getRandom(), "Заметка простая 3", getData()), false),
                Pair(Data(4, TYPE_SIMPLE, getRandom(), "Заметка простая 4", getData()), false),
                Pair(Data(5, TYPE_SIMPLE, getRandom(), "Заметка простая 5", getData()), false),
                Pair(Data(6, TYPE_SIMPLE, getRandom(), "Заметка простая 6", getData()), false)
            )
            true -> listOf(
                Pair(Data(0, TYPE_HEADER, 0, "Заголовок"), false),
                Pair(Data(1, TYPE_SIMPLE, getRandom(), "Заметка простая 7", getData()), false),
                Pair(Data(2, TYPE_SIMPLE, getRandom(), "Заметка простая 8", getData()), false),
                Pair(Data(3, TYPE_SIMPLE, getRandom(), "Заметка простая 3", getData()), false),
                Pair(Data(4, TYPE_SIMPLE, getRandom(), "Заметка простая 4", getData()), false),
                Pair(Data(5, TYPE_SIMPLE, getRandom(), "Заметка простая 9", getData()), false),
                Pair(Data(6, TYPE_SIMPLE, getRandom(), "Заметка простая 10", getData()), false)
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        adapter = RecyclerAdapter(data, callbackAddMars, callbackAddEarth, callbackRemove)
        binding.apply {
        recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)

        chipDiffUtil.setOnClickListener {
            changeAdapterData()
        }
        chipPriority.setOnClickListener {
            filterPriority()
        }
        chipDate.setOnClickListener {
            filterData()
        }
    }
    }


    val callbackAddMars = AddItem {

        val number = (data.size + 1).toString()
        data.add(
            it, Pair(
                Data(
                    number.toInt(), TYPE_SIMPLE, getRandom(),
                    "Заметка простая ${number}", getData()
                ), false
            )
        )
        dataSize = data.size
        adapter.setListDataAdd(data, it)
    }

    val callbackAddEarth = AddItem {

        val number = (data.size + 1).toString()
        data.add(
            it, Pair(
                Data(
                    number.toInt(), TYPE_REMIND, getRandom(),
                    "Заметка напоминание ${number}", getData()
                ), false
            )
        )
        dataSize = data.size
        adapter.setListDataAdd(data, it)
    }
    val callbackRemove = RemoveItem {

        data.removeAt(it)
        dataSize = data.size
        adapter.setListDataRemove(data, it)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView

        searchItem.apply {

            searchView.also {

                it.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { it1 -> filterName(it1) }
                        it.clearFocus()
                        it.setQuery("", false)
                        collapseActionView()

                        return true
                    }

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
        }

    }

    private fun filterName(query: String) {
        val lowerCaseQuery = query.lowercase(Locale.getDefault())
        val filteredModelList: MutableList<Pair<Data, Boolean>> = ArrayList()
        filteredModelList.add(Pair(Data(0, TYPE_HEADER, 0, "Заголовок"), false))
        for (model in data) {

            val text: String = model.first.name.lowercase(Locale.getDefault())
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }

        if (filteredModelList.size == 1) {
            Toast.makeText(context, "Заметка не найдена", Toast.LENGTH_LONG).show()
        } else {
            adapter.setFilteredList(filteredModelList)
        }

    }

    private fun filterPriority() {
        val sortedPriority = data.sortedWith(compareBy { it.first.priority }).toMutableList()
        adapter.setFilteredList(sortedPriority)
    }

    private fun filterData() {
        val sortedData = data.sortedWith(compareBy { it.first.dateCreate }).toMutableList()
        adapter.setFilteredList(sortedData)
    }

    private fun getData(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, EEE, HH:mm:ss z")
        val date = dateFormat.format(Date())
        return date
    }

    private fun getRandom() = (1..100).random()


}