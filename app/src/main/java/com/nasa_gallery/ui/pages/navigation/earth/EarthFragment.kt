package com.nasa_gallery.ui.pages.navigation.earth

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentEarthBinding
import com.nasa_gallery.ui.main.ViewBindingFragment

class EarthFragment : ViewBindingFragment<FragmentEarthBinding>(
    FragmentEarthBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapterForEarthFragment(this)

        bindTabLayout()
    }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.today)
                1 -> getString(R.string.yesterday)
                2 -> getString(R.string.the_day_before_yesterday)
                else -> getString(R.string.today)
            }
        }.attach()
    }

}
