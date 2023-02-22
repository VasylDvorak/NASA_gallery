package com.nasa_gallery.ui.view.navigation.system

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Explode
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.nasa_gallery.databinding.FragmentSystemBinding

class SystemFragment : Fragment() {
    private var _binding:  FragmentSystemBinding? = null
    private val binding get() = _binding!!

    var isFlag = false

    override fun onCreate (savedInstanceState: Bundle ?) {
        super .onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, true )
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, false )
        exitTransition = Explode()

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
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
                params.gravity =Gravity.TOP or Gravity.START

            } else {
                params.gravity = Gravity.BOTTOM or Gravity.END
            }
            binding.playPauseCircle.layoutParams = params

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
