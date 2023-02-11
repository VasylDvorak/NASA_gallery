package com.nasa_gallery.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityMainBinding
import com.nasa_gallery.view.picture.PictureOfTheDayFragment

const val MY_THEME_KEY= "my_theme_key"
const val NIGHT_DAY_THEME_KEY= "day_night_theme_key"
const val first_theme = R.style.IndigoTheme
const val second_theme = R.style.PinkTheme
const val third_theme = R.style.RedTheme_PinkTheme

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var value_my_theme = getPreferences(Context.MODE_PRIVATE)
            .getInt(MY_THEME_KEY, second_theme)
        setTheme(value_my_theme)

        var value_day_night_theme = getPreferences(Context.MODE_PRIVATE)
            .getInt(NIGHT_DAY_THEME_KEY, AppCompatDelegate.MODE_NIGHT_AUTO_TIME)
        AppCompatDelegate.setDefaultNightMode(value_day_night_theme)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

}