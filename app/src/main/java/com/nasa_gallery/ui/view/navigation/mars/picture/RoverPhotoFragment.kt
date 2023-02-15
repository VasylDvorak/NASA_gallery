package com.nasa_gallery.ui.view.navigation.mars.picture


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.nasa_gallery.R
import com.nasa_gallery.databinding.RoverPhotoFragmentBinding

import com.nasa_gallery.model.app.AppStateMars
import com.nasa_gallery.ui.view.view_model.MarsViewModel

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val MARS_BUNDLE = "mars_bundle"
const val CURIOSITY = 1
const val OPPORTUNITY = 2
const val SPIRIT = 3


class RoverPhotoFragment : Fragment() {

    private var _binding: RoverPhotoFragmentBinding? = null
    private val binding get() = _binding!!


    //Ленивая инициализация модели
    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this)[MarsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RoverPhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {


            val rover = (arguments?.getInt(MARS_BUNDLE) ?: Int) as Int

            when (rover){
                CURIOSITY -> { getDataFromServer("curiosity") }
                OPPORTUNITY -> {getDataFromServer("opportunity") }
                SPIRIT -> {getDataFromServer("spirit")}
            }

        }

    }

    private fun getDataFromServer(rover: String) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -365*2)
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateText: String = dateFormat.format(cal.time)
        viewModel.getData(dateText, rover).observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: AppStateMars) {
        binding.apply {
        when (data) {
            is AppStateMars.Success -> {
                loading.visibility = View.GONE
                val serverResponseData = data.serverResponseData
                val photos_list=serverResponseData.photos
                if (photos_list.size == 0) {
                    toast(getString(R.string.empty_link))
                } else {
                val url = photos_list[0].img_src

                if (url.isNullOrEmpty()) {
                    toast(getString(R.string.empty_link))
                } else {
                    loadImage(url)

                }}
        }
            is AppStateMars.Loading -> {
                loading.visibility = View.VISIBLE
            }
            is AppStateMars.Error -> {
                loading.visibility = View.GONE
                toast(data.error.message)
            }
        }
    }}


    private fun loadImage(url: String?) {
        val imageView = binding.imageView
        imageView.visibility = View.VISIBLE
        imageView.load(url) {
            lifecycle(this@RoverPhotoFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
            crossfade(true)
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


    companion object {
        fun newInstance(bundle: Bundle): RoverPhotoFragment {
            val fragment = RoverPhotoFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

}
