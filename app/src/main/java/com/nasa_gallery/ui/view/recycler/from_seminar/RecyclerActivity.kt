package com.nasa_gallery.ui.view.recycler.from_seminar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nasa_gallery.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = arrayListOf(
            Data(TYPE_HEADER,"Заголовок" ),
            Data(TYPE_EARTH,"Earth" ),
            Data(TYPE_EARTH,"Earth" ),
            Data(TYPE_MARS, "Mars" , "" ),
            Data(TYPE_EARTH,"Earth" ),
            Data(TYPE_EARTH,"Earth" ),
            Data(TYPE_MARS, "Mars"  )
        )
binding.recyclerView.adapter = RecyclerAdapter(data)
}}