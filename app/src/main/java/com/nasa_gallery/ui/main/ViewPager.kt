package com.nasa_gallery.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nasa_gallery.databinding.FragmentSplashBinding
import com.nasa_gallery.databinding.FragmentViewPagerBinding

class ViewPager : ViewBindingFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate)  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        bindTabLayout()
    }


    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "Земля"
                1 -> "Марс"
                2 -> "Система"
                3 -> "Картина"
                4 -> "Заметки"
                else -> "Земля"
            }
        }.attach()
    }

}
