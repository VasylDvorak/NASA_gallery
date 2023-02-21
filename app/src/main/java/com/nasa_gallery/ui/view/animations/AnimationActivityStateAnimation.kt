package com.nasa_gallery.ui.view.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasa_gallery.databinding.ActivityAnimationComplicatedAnimationBinding
import com.nasa_gallery.databinding.ActivityAnimationStateAnimationBinding

class AnimationActivityStateAnimation : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationStateAnimationBinding
    var isFlag = false
    var duration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationStateAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            binding.header.isSelected = binding.scrollView.canScrollVertically( -1 )
        }

}}
