package com.nasa_gallery.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityMainBinding
import com.nasa_gallery.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}