package com.nasa_gallery.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentSettingsBinding
import com.nasa_gallery.view.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreThemesCondition()
        setThemesCondition()
}

    private fun restoreThemesCondition(){
        var value_my_theme = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getInt(MY_THEME_KEY, second_theme)
        binding.apply {

            when (value_my_theme) {
                first_theme -> {
                    firstTheme.isChecked = true
                    secondTheme.isChecked = false
                    thirdTheme.isChecked = false
                }
                second_theme -> {
                    secondTheme.isChecked = true
                    firstTheme.isChecked = false
                    thirdTheme.isChecked = false}
                third_theme -> {
                    thirdTheme.isChecked = true
                    firstTheme.isChecked = false
                    secondTheme.isChecked = false}
            }



            var value_day_night_theme = activity?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(NIGHT_DAY_THEME_KEY, AppCompatDelegate.MODE_NIGHT_AUTO_TIME)


            when (value_day_night_theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    nightOrDay.visibility=View.VISIBLE
                    nightOrDay.isChecked = true
                    autoNightOrDay.isChecked = false
                    nightOrDay.text = getString(R.string.day_theme_now)

                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    nightOrDay.visibility=View.VISIBLE
                    nightOrDay.isChecked = false
                    autoNightOrDay.isChecked = false
                    nightOrDay.text = getString(R.string.night_theme_now)

                }
                AppCompatDelegate.MODE_NIGHT_AUTO_TIME -> {
                    autoNightOrDay.isChecked = true
                    nightOrDay.visibility=View.GONE}
            }}
    }

    private fun setThemesCondition(){
        binding.apply {
            firstTheme.setOnClickListener { renewActivity(first_theme) }
            secondTheme.setOnClickListener { renewActivity(second_theme) }
            thirdTheme.setOnClickListener { renewActivity(third_theme) }

            var newNightDayMode = AppCompatDelegate.MODE_NIGHT_AUTO_TIME
            nightOrDay.setOnClickListener {
                autoNightOrDay.isChecked=false
                when (nightOrDay.isChecked) {
                    true -> {newNightDayMode =AppCompatDelegate.MODE_NIGHT_NO
                        AppCompatDelegate.setDefaultNightMode(newNightDayMode) }
                    false -> {newNightDayMode =AppCompatDelegate.MODE_NIGHT_YES
                        AppCompatDelegate.setDefaultNightMode(newNightDayMode)} }
                saveDayNightTheme(newNightDayMode)
            }

            autoNightOrDay.setOnClickListener {
                if (!autoNightOrDay.isChecked)
                  {newNightDayMode =AppCompatDelegate.MODE_NIGHT_AUTO_TIME
                      nightOrDay.visibility=View.VISIBLE
                        AppCompatDelegate.setDefaultNightMode(newNightDayMode)}
                else{
                    nightOrDay.visibility=View.GONE
                }
                saveDayNightTheme(newNightDayMode)}
        }
    }

    private fun saveDayNightTheme(dayNight: Int) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(NIGHT_DAY_THEME_KEY, dayNight)
                apply()
            }
        }
    }

    private fun renewActivity(myTheme: Int) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(MY_THEME_KEY, myTheme)
                apply()
            }
  activity?.recreate()
     }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
