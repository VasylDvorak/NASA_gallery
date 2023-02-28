package com.nasa_gallery.ui.pages.navigation.earth.picture


import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
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
import com.nasa_gallery.databinding.FragmentPictureOfTheDayBinding
import com.nasa_gallery.domain.entity.model.PictureOfTheDayResponseData
import com.nasa_gallery.domain.Application.AppState
import com.nasa_gallery.ui.pages.navigation.description.BUNDLE_DESCRIPTION
import com.nasa_gallery.ui.pages.navigation.description.BUNDLE_TITLE
import com.nasa_gallery.ui.pages.navigation.description.CoordinatorFragment
import com.nasa_gallery.domain.entity.view_model.PictureOfTheDayViewModel
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

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough()
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
            title.text = titleText
            val description = getViewById(R.id.bottomSheetDescription) as TextView
            showExplanation(description, explanation)

        }
    }

    private fun showExplanation(description: TextView, explanation: String?) {
        //Show description
        description.text = explanation
        description.typeface = Typeface.createFromAsset(requireActivity().assets, "Aloevera.ttf")

val spanned: Spanned
val spannableString: SpannableString
val spannableStringBuilder: SpannableStringBuilder

 val text = "My text <ul><li>bullet one</li><li>bullet two</li></ul>"
       description.text = Html.fromHtml(text)




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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance(bundle: Bundle): PictureOfTheDayFragment {
            val fragment = PictureOfTheDayFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

}
