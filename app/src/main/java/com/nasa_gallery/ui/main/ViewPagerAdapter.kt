package com.nasa_gallery.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nasa_gallery.ui.pages.navigation.mars.MarsFragment
import com.nasa_gallery.ui.pages.navigation.notes.RecyclerFragment
import com.nasa_gallery.ui.pages.navigation.picture_artist.PictureFragment
import com.nasa_gallery.ui.pages.navigation.system.SystemFragment

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(
        MarsFragment(), SystemFragment(),
        PictureFragment(), RecyclerFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
