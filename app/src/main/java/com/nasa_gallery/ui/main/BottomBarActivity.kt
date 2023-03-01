package com.nasa_gallery.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityBootomBarBinding
import com.nasa_gallery.ui.pages.navigation.earth.EarthFragment
import com.nasa_gallery.ui.pages.navigation.mars.MarsFragment
import com.nasa_gallery.ui.pages.navigation.picture_artist.PictureFragment

import com.nasa_gallery.ui.pages.navigation.system.SystemFragment
import com.nasa_gallery.ui.pages.navigation.recycler_notes.RecyclerFragment
import com.nasa_gallery.ui.pages.navigation.recycler_notes.RecyclerFragment.Companion.dataSize

class BottomBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBootomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityBootomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_view_earth -> {
                    navigateTo(EarthFragment())
                }
                R.id.action_view_mars -> {
                    navigateTo(MarsFragment())
                }
                R.id.action_view_system -> {
                    navigateTo(SystemFragment())
                }
                R.id.action_view_picture -> {
                    navigateTo(PictureFragment())
                }
                R.id.action_view_notes -> {
                    navigateTo(RecyclerFragment())
                }
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.action_view_earth


        val badgeEarth = binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_earth)
        badgeEarth.apply {
            number = 3
            maxCharacterCount = 2
            badgeGravity = BadgeDrawable.TOP_END
        }


        val badgeMars = binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_mars)
        badgeMars.apply {
            number = 3
            maxCharacterCount = 2
            badgeGravity = BadgeDrawable.TOP_END
        }


        val badgeSystem = binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_system)
        badgeSystem.apply {
            number = 3
            maxCharacterCount = 2
            badgeGravity = BadgeDrawable.TOP_END
        }

        val badgeNotes = binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_notes)
        badgeNotes.apply {
            number = dataSize
            maxCharacterCount = 3
            badgeGravity = BadgeDrawable.TOP_END
        }

    }

    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(com.google.android.material.R.id.container, fragment).commit()
    }

}
