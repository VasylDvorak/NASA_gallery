package com.nasa_gallery.ui.view.navigation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nasa_gallery.ui.view.navigation.mars.MarsFragment
import com.nasa_gallery.ui.view.navigation.picture_artist.PictureFragment

import com.nasa_gallery.ui.view.navigation.system.SystemFragment
import com.nasa_gallery.ui.view.recycler.notes.RecyclerFragment

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(MarsFragment(), SystemFragment(),
        PictureFragment(), RecyclerFragment())


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
