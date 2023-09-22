package com.nasa_gallery.ui.view.navigation.mars

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nasa_gallery.ui.view.navigation.mars.picture.*


class ViewPagerAdapterForMarsFragment(fragment: Fragment) :
    FragmentStateAdapter(fragment){



  private val curiosity = RoverPhotoFragment.newInstance(Bundle()
      .apply { putInt(MARS_BUNDLE, CURIOSITY) })

    private val opportunity = RoverPhotoFragment.newInstance(Bundle()
        .apply { putInt(MARS_BUNDLE, OPPORTUNITY) })

    private val spirit = RoverPhotoFragment.newInstance(Bundle()
        .apply { putInt(MARS_BUNDLE, SPIRIT) })

  private val fragments = arrayOf(curiosity, opportunity, spirit )


    override fun getItemCount(): Int {
return fragments.size    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
