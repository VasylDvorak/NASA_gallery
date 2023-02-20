package com.nasa_gallery.ui.view.animations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityBootomBarBinding
import com.nasa_gallery.ui.view.navigation.earth.EarthFragment
import com.nasa_gallery.ui.view.navigation.mars.MarsFragment
import com.nasa_gallery.ui.view.navigation.system.SystemFragment

class AnimationActivity : AppCompatActivity() {

private lateinit var binding:ActivityBootomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBootomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)



}}
