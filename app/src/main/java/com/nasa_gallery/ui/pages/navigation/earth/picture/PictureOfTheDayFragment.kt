package com.nasa_gallery.ui.pages.navigation.earth.picture


import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.TypefaceSpan
import android.view.Gravity
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.nasa_gallery.R
import com.nasa_gallery.data.net.model.PictureOfTheDayResponseData
import com.nasa_gallery.databinding.FragmentPictureOfTheDayBinding
import com.nasa_gallery.domain.application.AnswerFromServerStatePictureOfTheDay
import com.nasa_gallery.ui.main.ViewBindingFragment
import com.nasa_gallery.ui.pages.navigation.description.BUNDLE_DESCRIPTION
import com.nasa_gallery.ui.pages.navigation.description.BUNDLE_TITLE
import com.nasa_gallery.ui.pages.navigation.description.CoordinatorFragment
import com.nasa_gallery.ui.view_models.PictureOfTheDayViewModel
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


const val EARTH_BUNDLE = "earth_bundle"
const val TODAY = 1
const val YESTERDAY = 2
const val THE_DAY_BEFORE_YESTERDAY = 3
const val IMAGE = "image"
const val VIDEO = "video"
const val hartEffect = 500
const val duration = 2000L

class PictureOfTheDayFragment : ViewBindingFragment<FragmentPictureOfTheDayBinding>(
    FragmentPictureOfTheDayBinding::inflate
) {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstStart) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isAdded)// проверяем, не умер ли фрагент
                    showTuturial()
            }, 500)
            firstStart = false
        }

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
                showDialog(view)
            }

        }

    }

    private fun showDialog(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(
                10f, 2f,
                Shader.TileMode.CLAMP
            )
            view.findViewById<CoordinatorLayout>(R.id.main_coordinator).setRenderEffect(blurEffect)
        }
        val builder = AlertDialog.Builder(requireContext())
        builder
            .setTitle(R.string.about_program)
            .setIcon(android.R.drawable.ic_menu_info_details)
            .setMessage(R.string.message_dialog)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                view.findViewById<CoordinatorLayout>(R.id.main_coordinator).setRenderEffect(null)
                dialog.cancel()
            }
            .setOnDismissListener {
                view.findViewById<CoordinatorLayout>(R.id.main_coordinator).setRenderEffect(null)
            }
        builder.create()
        builder.show()

    }

    private fun getDataFromServer(decriment: Int) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, decriment)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateText: String = dateFormat.format(cal.time)
        viewModel.getData(dateText).observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: AnswerFromServerStatePictureOfTheDay) {
        binding.apply {
            when (data) {
                is AnswerFromServerStatePictureOfTheDay.Success -> {
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
                is AnswerFromServerStatePictureOfTheDay.Loading -> {
                    loading.visibility = View.VISIBLE
                }
                is AnswerFromServerStatePictureOfTheDay.Error -> {
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
        showComponentsZoom()
    }

    var isFlag = false
    private fun showComponentsZoom() {

        binding.imageView.setOnClickListener {
            val params = it.layoutParams as FrameLayout.LayoutParams
            isFlag = !isFlag

            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()

            changeBounds.duration = duration
            changeBounds.setPathMotion(MaterialArcMotion())
            changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
            changeImageTransform.duration = duration
            transitionSet.addTransition(changeBounds) // важен порядок
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            params.apply {
                if (isFlag) {
                    gravity = Gravity.TOP or Gravity.CENTER
                    height = FrameLayout.LayoutParams.MATCH_PARENT + hartEffect
                    width = FrameLayout.LayoutParams.MATCH_PARENT + hartEffect
                    (it as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                    gravity = Gravity.BOTTOM or Gravity.CENTER
                    height = FrameLayout.LayoutParams.WRAP_CONTENT
                    width = FrameLayout.LayoutParams.WRAP_CONTENT
                    binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    it.layoutParams = params
                }
                binding.imageView.layoutParams = params
            }
        }
    }


    private fun setBottomSheetBehavior(
        serverResponseData: PictureOfTheDayResponseData,
        bottomSheet: ConstraintLayout
    ) {
        val titleText = serverResponseData.title
        val explanation = serverResponseData.explanation



        binding.descriptionFab.setOnClickListener {
            showDescription(titleText, explanation)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        with(bottomSheet) {
            val title = getViewById(R.id.bottomSheetDescriptionHeader) as TextView
            //   showTitle(title, titleText)
            title.text = titleText
            val description = getViewById(R.id.bottomSheetDescription) as TextView
            description.text = explanation
            //   showExplanation(description, explanation)

        }
    }

    private fun showTitle(title: TextView, titleText: String?) {
        title.typeface = Typeface.createFromAsset(
            requireActivity().assets,
            "font/Lobster-Regular.ttf"
        )
        var spannableStringBuilder: SpannableStringBuilder
        spannableStringBuilder = SpannableStringBuilder(titleText)
        title.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
        spannableStringBuilder = title.text as SpannableStringBuilder
        val textLength = titleText!!.length
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            ), 0, (textLength / 3).toInt(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color3
                )
            ),
            (textLength / 3).toInt(),
            (2 * textLength / 3).toInt(),
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.indigo
                )
            ), (2 * textLength / 3).toInt(), textLength, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    private fun showExplanation(description: TextView, explanation: String?) {


        description.typeface = Typeface.createFromAsset(requireActivity().assets, "Aloevera.ttf")

        var spannableStringBuilder: SpannableStringBuilder

        val text = explanation

        spannableStringBuilder = SpannableStringBuilder(text)
        description.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
        spannableStringBuilder = description.text as SpannableStringBuilder


        for (i in text!!.indices) {
            if (text[i] == ',') {
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.teal_700
                        )
                    ), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }

        val verticalAlignment = DynamicDrawableSpan.ALIGN_BASELINE
        val bitmap = ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)!!.toBitmap()
        for (i in text.indices) {
            if (text[i] == '.') {
                spannableStringBuilder.setSpan(
                    ImageSpan(requireContext(), bitmap, verticalAlignment),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        spannableStringBuilder.replace(explanation.length, explanation.length, "@")

        val request = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Amita", R.array.com_google_android_gms_fonts_certs
        )
        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                typeface?.let {

                    spannableStringBuilder.setSpan(
                        TypefaceSpan(it), 0, spannableStringBuilder.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                super.onTypefaceRetrieved(typeface)
            }
        }
        FontsContractCompat.requestFont(
            requireContext(), request, callback,
            Handler(Looper.getMainLooper())
        )
    }


    private fun showDescription(title: String?, description: String?) {


        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, CoordinatorFragment.newInstance(Bundle().apply {
                putString(BUNDLE_TITLE, title)
                putString(BUNDLE_DESCRIPTION, description)
            }))
            ?.addToBackStack("")
            ?.commit()
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    private fun showTuturial() {

        GuideView.Builder(requireContext())
            .setTitle(getString(R.string.tutorial_title))
            .setContentText(getString(R.string.tutorial_description))
            .setTargetView(binding.descriptionFab)
            .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
            .build()
            .show()


    }

    companion object {
        private var firstStart: Boolean = true
        fun newInstance(bundle: Bundle): PictureOfTheDayFragment {
            val fragment = PictureOfTheDayFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

}
