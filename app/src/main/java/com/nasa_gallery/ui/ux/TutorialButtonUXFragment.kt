package com.nasa_gallery.ui.ux

import android.app.ProgressDialog.show
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.nasa_gallery.databinding.FragmentUxButtonBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType


class TutorialButtonUXFragment :ViewBindingFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {


    companion object {
        fun newInstance() = TutorialButtonUXFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded)// проверяем, не умер ли фрагент
                show()
        }, 500)
    }

    private fun show() {

        GuideView.Builder(requireContext())
            .setTitle("Guide Title Text")
            .setContentText("Guide Description Text\n .....Guide Description Text\n .....Guide Description Text .....")
            .setTargetView(binding.btnBad)
            .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
            .build()
            .show()


    }



}