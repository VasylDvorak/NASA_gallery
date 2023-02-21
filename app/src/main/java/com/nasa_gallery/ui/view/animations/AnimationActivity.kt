package com.nasa_gallery.ui.view.animations

import android.os.Bundle
import android.view.Gravity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.google.android.material.badge.BadgeDrawable
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityAnimationBinding
import com.nasa_gallery.databinding.ActivityBootomBarBinding
import com.nasa_gallery.ui.view.navigation.earth.EarthFragment
import com.nasa_gallery.ui.view.navigation.mars.MarsFragment
import com.nasa_gallery.ui.view.navigation.system.SystemFragment

class AnimationActivity : AppCompatActivity() {

private lateinit var binding: ActivityAnimationBinding
var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

binding.button.setOnClickListener{
    isFlag = !isFlag
    val myAutoTransition = TransitionSet()
   // myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
    myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
    val fade = Slide(Gravity.END)
    fade.duration = 1000L
    val changeBounds = ChangeBounds()
    changeBounds.duration = 1000L
    myAutoTransition.addTransition(changeBounds)
    myAutoTransition.addTransition(fade)
    myAutoTransition.duration = 2000L
    TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)
    binding.text.visibility = if(isFlag) View.VISIBLE else {View.GONE}

}

}}
