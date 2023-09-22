package com.nasa_gallery.ui.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentSplashBinding


class SplashFragment : ViewBindingFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
lateinit var activityInterractor : ActivityInterractor
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rainbowText(appName, appName.text.toString())
            val fullTime = 2000f
            splashPicture.animate().rotation(360f)
                .setDuration(fullTime.toLong()).start()
            object: CountDownTimer(fullTime.toLong() , 5L){
                override fun onTick(p0: Long) {
                    val process = ((1- p0/fullTime)*100).toInt()
                    if( progress.progress !=process)
                        progress.progress = process
                }

                override fun onFinish() {
                    activity?.supportFragmentManager?.popBackStack()

                }
            }.start()
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityInterractor) {
            this.activityInterractor = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityInterractor.onFragmentClosed()
    }

}