package com.nasa_gallery.ui.view.navigation.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Explode
import androidx.transition.Fade
import com.google.android.material.transition.MaterialFadeThrough
import com.nasa_gallery.databinding.FragmentCoordinatorBinding
import com.nasa_gallery.ui.view.navigation.earth.picture.EARTH_BUNDLE

const val BUNDLE_DESCRIPTION = "description"
const val BUNDLE_TITLE = "title"

class CoordinatorFragment : Fragment() {
    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() = _binding!!
    override fun onCreate (savedInstanceState: Bundle ?) {
        super .onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
