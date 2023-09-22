package com.nasa_gallery.ui.pages.navigation.mars

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentMarsBinding
import com.nasa_gallery.ui.main.ViewBindingFragment


class MarsFragment : ViewBindingFragment<FragmentMarsBinding>(
    FragmentMarsBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reenterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, false
        )
        exitTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z, true
        )

        exitTransition = Hold()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapterForMarsFragment(this)

        bindTabLayout()

    }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.curiosity)
                1 -> getString(R.string.opportunity)
                2 -> getString(R.string.spirit)
                else -> getString(R.string.curiosity)
            }
        }.attach()
    }

}