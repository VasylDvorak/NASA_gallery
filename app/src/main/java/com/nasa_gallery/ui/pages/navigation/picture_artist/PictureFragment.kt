package com.nasa_gallery.ui.pages.navigation.picture_artist

import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.IconMarginSpan
import android.text.style.LineHeightSpan
import android.text.style.MaskFilterSpan
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentPictureBinding
import com.nasa_gallery.ui.main.ViewBindingFragment

class PictureFragment : ViewBindingFragment<FragmentPictureBinding>(
    FragmentPictureBinding::inflate
) {
    var isFlag = false
    var duration = 2000L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resources.getString(R.string.title_picture)
        titlePictureSpan(binding.title, getString(R.string.title_picture))
        pictureDescriptionSpan(binding.description, getString(R.string.picture_descripion))
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

    private fun pictureDescriptionSpan(description: TextView, text: String) {

        val spannableString = SpannableString(text)
        val startIndex = 0
        val endIndex = text.length
        val flag = Spannable.SPAN_INCLUSIVE_INCLUSIVE  // 0: no flag;

        val lineHeightInPx = 100
        spannableString.setSpan(LineHeightSpan.Standard(lineHeightInPx), startIndex, endIndex, flag)

        description.text = spannableString
    }

    private fun titlePictureSpan(title: TextView, text: String) {

        val spannableString = SpannableString(text)
        val startIndex = 0
        val endIndex = text.length
        val flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        val bitmap = ContextCompat.getDrawable(requireContext(), R.drawable.ic_system)!!.toBitmap()
        val paddingInPx = 50
        val blurRadius = 5f
        val blurMaskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.INNER)
        spannableString.setSpan(IconMarginSpan(bitmap, paddingInPx), startIndex, endIndex, flag)
        spannableString.setSpan(MaskFilterSpan(blurMaskFilter), startIndex, endIndex, flag)

        title.text = spannableString
    }

}



