package com.nasa_gallery.ui.pages.navigation.earth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nasa_gallery.ui.pages.navigation.earth.picture.*



class ViewPagerAdapterForEarthFragment(fragment: Fragment) :
    FragmentStateAdapter(fragment){

  private val today = PictureOfTheDayFragment.newInstance(Bundle()
      .apply { putInt(EARTH_BUNDLE, TODAY) })

    private val yesterday  = PictureOfTheDayFragment.newInstance(Bundle()
        .apply { putInt(EARTH_BUNDLE, YESTERDAY) })

    private val dayBeforeYesterday  = PictureOfTheDayFragment.newInstance(Bundle()
        .apply { putInt(EARTH_BUNDLE, THE_DAY_BEFORE_YESTERDAY) })

  private val fragments = arrayOf(today, yesterday, dayBeforeYesterday )


    override fun getItemCount(): Int {
return fragments.size    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
