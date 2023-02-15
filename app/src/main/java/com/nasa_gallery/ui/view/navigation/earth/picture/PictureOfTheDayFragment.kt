package com.nasa_gallery.ui.view.navigation.earth.picture


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.nasa_gallery.R
import com.nasa_gallery.databinding.FragmentPictureOfTheDayBinding
import com.nasa_gallery.model.PictureOfTheDayResponseData
import com.nasa_gallery.model.app.AppState
import com.nasa_gallery.ui.view.view_model.PictureOfTheDayViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val EARTH_BUNDLE = "earth_bundle"
const val TODAY = 1
const val YESTERDAY = 2
const val THE_DAY_BEFORE_YESTERDAY = 3
const val IMAGE = "image"
const val VIDEO = "video"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
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


        binding.apply {
            inputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse(
                            "https://en.wikipedia.org/wiki/${
                                binding.inputEditText
                                    .text.toString()
                            }"
                        )
                })
            }
            extendedFab.extendMotionSpec
            val day = (arguments?.getInt(EARTH_BUNDLE) ?: Int) as Int

            when (day) {
                TODAY -> {
                    getDataFromServer(0)
                }
                YESTERDAY -> {
                    getDataFromServer(-1)
                }
                THE_DAY_BEFORE_YESTERDAY -> {
                    getDataFromServer(-2)
                }
            }

            extendedFab.setOnClickListener {
                showAlertDialog(
                    R.string.about_program, R.string.message_dialog,
                    android.R.drawable.ic_menu_info_details, R.string.yes
                )
            }
        }
    }

    private fun getDataFromServer(decriment: Int) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, decriment)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateText: String = dateFormat.format(cal.time)
        viewModel.getData(dateText).observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: AppState) {
        binding.apply {
            when (data) {
                is AppState.Success -> {
                    loading.visibility = View.GONE
                    val serverResponseData = data.serverResponseData
                    val url = serverResponseData.url
                    val media_type = serverResponseData.mediaType
                    if ((url.isNullOrEmpty()) && (media_type.isNullOrEmpty())) {
                        toast(getString(R.string.empty_link))
                    } else {
                        imageView.visibility = View.GONE
                        webViewPlayer.visibility = View.GONE

                        when (media_type) {
                            IMAGE -> {
                                loadImage(url)
                            }
                            VIDEO -> {
                                loadVideo(url)
                            }

                        }

                        view?.let {
                            setBottomSheetBehavior(
                                serverResponseData,
                                it.findViewById(R.id.bottom_sheet_container)
                            )
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
        }
    }

    private fun loadVideo(url: String?) {
        val webView = binding.webViewPlayer
        webView.visibility = View.VISIBLE
        val settings = webView.settings
        webView.loadUrl(url!!)
        settings.apply {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
        }
    }

    private fun loadImage(url: String?) {
        val imageView = binding.imageView
        imageView.visibility = View.VISIBLE
        imageView.load(url) {
            lifecycle(this@PictureOfTheDayFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
            crossfade(true)
        }
    }


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
        fun newInstance(bundle: Bundle): PictureOfTheDayFragment {
            val fragment = PictureOfTheDayFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

}
