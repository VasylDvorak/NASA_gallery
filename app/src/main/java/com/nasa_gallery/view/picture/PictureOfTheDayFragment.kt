package com.nasa_gallery.view.picture


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentPictureOfTheDayBinding
import com.nasa_gallery.model.PictureOfTheDayResponseData
import com.nasa_gallery.model.retrofit.AppState
import com.nasa_gallery.view.MainActivity
import com.nasa_gallery.view.settings.SettingsFragment
import com.nasa_gallery.view_model.PictureOfTheDayViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromServer(0)
        binding.apply {
            inputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText
                            .text.toString()}")
                })
            }
            extendedFab.extendMotionSpec


            chipToday.setOnClickListener { getDataFromServer(0) }
            chipYesterday.setOnClickListener { getDataFromServer(-1) }
            chipTheDayBeforeYesterday.setOnClickListener { getDataFromServer(-2) }
            extendedFab.setOnClickListener {
                showAlertDialog(
                    R.string.about_program, R.string.message_dialog,
                    android.R.drawable.ic_menu_info_details, R.string.yes)
            }
        }
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setBottomAppBar(view)
    }

    fun getDataFromServer(decriment: Int) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, decriment)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateText: String = dateFormat.format(cal.time)
        viewModel.getData(dateText).observe(viewLifecycleOwner) { renderData(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.hide(this)?.add(R.id.container, SettingsFragment.newInstance())
                ?.addToBackStack("")
                ?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: AppState) {
        binding.apply {
        when (data) {
            is AppState.Success -> {
                loading.visibility = View.GONE
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast(getString(R.string.empty_link))
                } else {
                    imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                        view?.let {
                            setBottomSheetBehavior(
                                serverResponseData,
                                it.findViewById(R.id.bottom_sheet_container)
                            )
                        }
                    }
                }
            }
            is AppState.Loading -> {
                loading.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loading.visibility = View.GONE
                toast(data.error.message)
            }
        }
    }}

    private fun setBottomAppBar(view: View) {
        binding.apply {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottomAppBar.navigationIcon = null
                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

                fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }}

    private fun setBottomSheetBehavior(
        serverResponseData: PictureOfTheDayResponseData,
        bottomSheet: ConstraintLayout
    ) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        with(bottomSheet) {
            val title = getViewById(R.id.bottomSheetDescriptionHeader) as TextView
            title.text = serverResponseData.title
            val description = getViewById(R.id.bottomSheetDescription) as TextView
            description.text = serverResponseData.explanation
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showAlertDialog(
        title: Int,
        message: Int,
        icon: Int,
        positive_button: Int
    ) {
        val builder = AlertDialog.Builder(this.requireContext())

        builder.run {
            setTitle(title)
            //set message for alert dialog
            setMessage(message)
            setIcon(icon)
            setPositiveButton(positive_button) { dialogInterface, which ->
                builder
            }
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
