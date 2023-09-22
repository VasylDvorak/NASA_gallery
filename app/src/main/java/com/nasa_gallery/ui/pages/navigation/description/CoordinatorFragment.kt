package com.nasa_gallery.ui.pages.navigation.description

import android.os.Bundle
import android.view.View
import androidx.transition.Fade
import com.google.android.material.transition.MaterialFadeThrough
import com.nasa_gallery.databinding.FragmentCoordinatorBinding
import com.nasa_gallery.ui.main.ViewBindingFragment
import com.nasa_gallery.ui.pages.navigation.earth.picture.EARTH_BUNDLE

const val BUNDLE_DESCRIPTION = "description"
const val BUNDLE_TITLE = "title"

class CoordinatorFragment :
    ViewBindingFragment<FragmentCoordinatorBinding>(FragmentCoordinatorBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = Fade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title: String = (arguments?.getString(BUNDLE_TITLE) ?: String) as String
        val description: String = (arguments?.getString(BUNDLE_DESCRIPTION) ?: String) as String
        val day = (arguments?.getInt(EARTH_BUNDLE) ?: Int) as Int

        binding.apply {
            titleText.text = title
            descriptionText.text = description
            back.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }


    companion object {
        fun newInstance(bundle: Bundle): CoordinatorFragment {
            val fragment = CoordinatorFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
