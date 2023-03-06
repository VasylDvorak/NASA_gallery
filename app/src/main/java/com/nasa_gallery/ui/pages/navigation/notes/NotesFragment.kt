package com.nasa_gallery.ui.pages.navigation.notes

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.nasa_gallery.R
import com.nasa_gallery.data.net.model.*
import com.nasa_gallery.data.notes.model_notes.DataForNotes
import com.nasa_gallery.data.notes.model_notes.TYPE_HEADER
import com.nasa_gallery.data.notes.model_notes.TYPE_REMIND
import com.nasa_gallery.data.notes.model_notes.TYPE_SIMPLE
import com.nasa_gallery.databinding.FragmentRecyclerBinding
import com.nasa_gallery.ui.main.ViewBindingFragment
import com.nasa_gallery.ui.view_models.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


class NotesFragment : ViewBindingFragment<FragmentRecyclerBinding>(
    FragmentRecyclerBinding::inflate
) {
    companion object {
        var dataSize = 0
    }
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this)[NotesViewModel::class.java]
    }
    lateinit var data : MutableList<Pair<DataForNotes, Boolean>>
    private var isNewList = false
    lateinit var adapter: RecyclerAdapter

    private fun changeAdapterData() {
        viewModel.getDataForNotesForDiffUtil(isNewList).observe(viewLifecycleOwner) { diffUtilData ->

        adapter.setListDataForDiffUtil(diffUtilData.map { it }.toMutableList())
        isNewList = !isNewList
    }}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)

        viewModel.getDataForNotes().observe(viewLifecycleOwner) { data =it


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
    }}


    val callbackAddMars = AddItem {
        val number = (data.size + 1).toString()
        data.add(
            it, Pair(
                DataForNotes(
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
                DataForNotes(
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
        val filteredModelList: MutableList<Pair<DataForNotes, Boolean>> = ArrayList()
        filteredModelList.add(Pair(DataForNotes(0, TYPE_HEADER, 0, getString(R.string.header_notes)), false))
        for (model in data) {

            val text: String = model.first.name.lowercase(Locale.getDefault())
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }

        if (filteredModelList.size == 1) {
            Toast.makeText(context, getString(R.string.noye_cant_find), Toast.LENGTH_LONG).show()
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
