package com.nasa_gallery.ui.view.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nasa_gallery.ui.view.navigation.mars.MarsFragment
import com.nasa_gallery.ui.view.navigation.picture_artist.PictureFragment

import com.nasa_gallery.ui.view.navigation.system.SystemFragment

class ViewPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(MarsFragment(), SystemFragment(), PictureFragment())


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
