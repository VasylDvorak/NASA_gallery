package com.nasa_gallery.ui.view.animations

import android.graphics.Rect
import android.os.Bundle
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityAnimationSecondBinding
import com.nasa_gallery.databinding.ActivityAnimationZoomBinding

class AnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationZoomBinding
    var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationZoomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageView.setOnClickListener {
            isFlag = !isFlag
            val params = it.layoutParams as LinearLayout.LayoutParams
            val transitionSet = TransitionSet()
val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            changeBounds.duration =2000L
            changeImageTransform.duration =2000L
            transitionSet.addTransition(changeBounds) // важен порядок
            transitionSet.addTransition(changeImageTransform)


            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if(isFlag){
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
            }
else{
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                it.layoutParams = params

}


    }
}}