package com.nasa_gallery.ui.view.animations

import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.transition.TransitionManager
import com.nasa_gallery.databinding.ActivityAnimationMixBinding

class AnimationActivityMix : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationMixBinding
    var isFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationMixBinding.inflate(layoutInflater)
        setContentView(binding.root)


val titles:MutableList <String> = ArrayList()
        for(i in 0..4){
            titles.add("Item $i")
        }



        binding.button.setOnClickListener {
            isFlag = !isFlag
            TransitionManager.beginDelayedTransition(binding.root)
            binding.transitionContainer.removeAllViews()
titles.shuffle()
titles.forEach{
    binding.transitionContainer.addView(TextView(this).apply{
        text = it
        ViewCompat.setTransitionName(this, it) // задали псевданим

    })

}
        }
    }
}