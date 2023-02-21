package com.nasa_gallery.ui.view.animations

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.nasa_gallery.R
import com.nasa_gallery.databinding.ActivityAnimationSecondBinding

class AnimationActivityTransitionExploded : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationSecondBinding
    var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        isFlag = !isFlag

//        val myAutoTransition = TransitionSet()
//        // myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//        myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
//        val fade = Slide(Gravity.END)
//        fade.duration = 1000L
//        val changeBounds = ChangeBounds()
//        changeBounds.duration = 1000L
//        myAutoTransition.addTransition(changeBounds)
//        myAutoTransition.addTransition(fade)
//        myAutoTransition.duration = 2000L
//        TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)

        binding.recycleView.adapter = Adapter()

    }

    inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activiry_animation_explode_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val rect = Rect()
                it.getGlobalVisibleRect(rect)
                val explode = Explode()
                explode.duration = 2000L
                explode.epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition): Rect {
                        return rect
                    }
                }
                TransitionManager.beginDelayedTransition(binding.recycleView, explode)
                binding.recycleView.adapter = null
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}
