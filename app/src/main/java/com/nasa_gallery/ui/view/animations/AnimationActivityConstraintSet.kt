package com.nasa_gallery.ui.view.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityAnimationConstraintSetStartBinding

class AnimationActivityConstraintSet : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationConstraintSetStartBinding
    var isFlag = false
    var duration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationConstraintSetStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tap.setOnClickListener{
            isFlag = !isFlag
            val changeBounds = ChangeBounds()
            changeBounds.duration =1000L
            changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
            val constraintSetStart = ConstraintSet()
            val constraintSetEnd = ConstraintSet()
            constraintSetStart.clone(this, R.layout.activity_animation_constraint_set_start)
            constraintSetEnd.clone(this, R.layout.activity_animation_constraint_set_end)

            if(isFlag){
constraintSetEnd.applyTo(binding.constraintContainer)

            }else{

                constraintSetStart.applyTo(binding.constraintContainer)
            }
        }


        }
    }
