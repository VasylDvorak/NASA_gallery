package com.nasa_gallery.ui.view.animations

import android.os.Bundle

import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.nasa_gallery.databinding.ActivityAnimationTraectoryBinding

class AnimationActivityTraectory : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationTraectoryBinding
    var isFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationTraectoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            isFlag = !isFlag
            val params = it.layoutParams as FrameLayout.LayoutParams
            var changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeBounds.setPathMotion(ArcMotion())
            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
            if (isFlag) {
                params.gravity =Gravity.TOP or Gravity.START
            } else {
                params.gravity = Gravity.BOTTOM or Gravity.END

            }
            binding.button.layoutParams = params

        }
    }
}