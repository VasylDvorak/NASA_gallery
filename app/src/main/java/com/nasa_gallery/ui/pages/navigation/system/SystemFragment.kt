package com.nasa_gallery.ui.pages.navigation.system

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.transition.ChangeBounds
import androidx.transition.Explode
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.nasa_gallery.databinding.FragmentSystemBinding
import com.nasa_gallery.ui.main.ViewBindingFragment

class SystemFragment : ViewBindingFragment<FragmentSystemBinding>(
    FragmentSystemBinding::inflate
) {

    var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, true
        )
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, false
        )
        exitTransition = Explode()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playPauseCircle.setOnClickListener {
            isFlag = !isFlag
            val params = it.layoutParams as FrameLayout.LayoutParams
            var changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeBounds.setPathMotion(MaterialArcMotion())
            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
            if (isFlag) {
                params.gravity = Gravity.TOP or Gravity.START

            } else {
                params.gravity = Gravity.BOTTOM or Gravity.END
            }
            binding.playPauseCircle.layoutParams = params

        }

    }

}
