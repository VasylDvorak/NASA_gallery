package com.nasa_gallery.ui.view.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nasa_gallery.databinding.ActivityAnimationComplicatedAnimationBinding

class AnimationActivityComplicatedAnimation : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationComplicatedAnimationBinding
    var isFlag = false
    var duration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationComplicatedAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val titles: MutableList<String> = ArrayList()
        for (i in 0..4) {
            titles.add("Item $i")
        }



        binding.fab.setOnClickListener {
            isFlag = !isFlag
            if (isFlag) {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 675f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -150f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -270f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.5f) .setDuration(duration).start()
                binding.optionOneContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                            binding.optionOneContainer.isClickable = true
                            binding.optionOneContainer.setOnClickListener {
                                Toast.makeText(applicationContext, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    }
                )

                binding.optionTwoContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                            binding.optionTwoContainer.isClickable = true
                            binding.optionTwoContainer.setOnClickListener {
                                Toast.makeText(applicationContext, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    })

            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 675f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f) .setDuration(duration).start()
                binding.optionOneContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                            binding.optionOneContainer.isClickable = false
                            binding.optionOneContainer.setOnClickListener {
                                Toast.makeText(applicationContext, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    })

                binding.optionTwoContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                            binding.optionTwoContainer.isClickable = false
                            binding.optionTwoContainer.setOnClickListener {
                                Toast.makeText(applicationContext, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    })


            }
        }
    }
}
