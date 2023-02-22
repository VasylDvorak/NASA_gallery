package com.nasa_gallery.ui.view.navigation.picture_artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentPictureBinding

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    var isFlag = false
    var duration = 2000L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPictureBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tap.setOnClickListener {
            isFlag = !isFlag
            val changeBounds = android.transition.ChangeBounds()
            changeBounds.duration = duration
            changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
            android.transition.TransitionManager.beginDelayedTransition(
                binding.constraintContainer,
                changeBounds
            )
            val constraintSetStart = ConstraintSet()
            constraintSetStart.clone(context, R.layout.fragment_picture)

            if (isFlag) {
                constraintSetStart.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.RIGHT
                )
                constraintSetStart.connect(
                    R.id.description,
                    ConstraintSet.LEFT,
                    R.id.backgroundImage,
                    ConstraintSet.LEFT
                )

            } else {
                constraintSetStart.connect(
                    R.id.title,
                    ConstraintSet.RIGHT,
                    R.id.backgroundImage,
                    ConstraintSet.LEFT
                )

                constraintSetStart.connect(
                    R.id.description,
                    ConstraintSet.LEFT,
                    R.id.backgroundImage,
                    ConstraintSet.RIGHT
                )

            }
            constraintSetStart.applyTo(binding.constraintContainer)

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}