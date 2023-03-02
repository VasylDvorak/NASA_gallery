package com.nasa_gallery.ui.ux

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityUxBinding

class UXActivity : AppCompatActivity() {
    lateinit var binding: ActivityUxBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(R.style.ThemeNASAgallery)

        binding = ActivityUxBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.bottomNavigationViewUX.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_ux_text -> {
                    navigateTo(TextUXFragment())
                }
                R.id.fragment_ux_button -> {
                    navigateTo(ButtonUXFragment())
                }
                R.id.fragment_ux_tutorial -> {
                    navigateTo(TutorialButtonUXFragment.newInstance())
                }
            }
            true
        }

        binding.bottomNavigationViewUX.selectedItemId = R.id.fragment_ux_button
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}