package com.nasa_gallery.ui.view.navigation.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentMarsBinding


class MarsFragment : Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}