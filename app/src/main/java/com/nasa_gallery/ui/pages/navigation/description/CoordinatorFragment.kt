package com.nasa_gallery.ui.pages.navigation.description

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import com.google.android.material.transition.MaterialFadeThrough
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentCoordinatorBinding
import com.nasa_gallery.ui.pages.navigation.earth.picture.EARTH_BUNDLE

const val BUNDLE_DESCRIPTION = "description"
const val BUNDLE_TITLE = "title"

class CoordinatorFragment : Fragment() {
    private var _binding: FragmentCoordinatorBinding? = null
    private val binding: FragmentCoordinatorBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            rainbowText(descriptionText, description)
            back.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    lateinit var spannableRainbow: SpannableString
    private fun rainbowText(description: TextView, explanation: String?) {

        description.typeface = Typeface.createFromAsset(requireActivity().assets, "Aloevera.ttf")

        spannableRainbow = SpannableString(explanation)
        rainbow(0, description)


    }

    fun rainbow(i: Int = 0, description: TextView) {
        var currentCount = i
        val x = object : CountDownTimer(20000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                colorText(currentCount, description)
                currentCount = if (++currentCount > 6) 0 else currentCount
            }

            override fun onFinish() {
                rainbow(currentCount, description)
            }
        }
        x.start()
    }


    private fun colorText(
        colorFirstNumber: Int,
        description: TextView
    ) {

        description.setText(spannableRainbow, TextView.BufferType.SPANNABLE)
        spannableRainbow = description.text as SpannableString
        var map = mutableMapOf<Int, Int>()
        try {

            map = mutableMapOf(
                0 to ContextCompat.getColor(requireContext(), R.color.red),
                1 to ContextCompat.getColor(requireContext(), R.color.orange),
                2 to ContextCompat.getColor(requireContext(), R.color.yellow),
                3 to ContextCompat.getColor(requireContext(), R.color.green),
                4 to ContextCompat.getColor(requireContext(), R.color.blue),
                5 to ContextCompat.getColor(requireContext(), R.color.purple_700),
                6 to ContextCompat.getColor(requireContext(), R.color.purple_500)
            )


            val spans = spannableRainbow.getSpans(
                0, spannableRainbow.length,
                ForegroundColorSpan::class.java
            )
            for (span in spans) {
                spannableRainbow.removeSpan(span)
            }

            var colorNumber = colorFirstNumber
            for (i in 0 until description.text.length) {
                if (colorNumber == 6) colorNumber = 0 else colorNumber += 1
                spannableRainbow.setSpan(
                    ForegroundColorSpan(map.getValue(colorNumber)),
                    i, i + 1,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        } catch (e: IllegalStateException) {
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
