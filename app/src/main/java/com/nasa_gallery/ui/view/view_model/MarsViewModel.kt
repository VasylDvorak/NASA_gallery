package com.nasa_gallery.ui.view.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa_gallery.BuildConfig
import com.nasa_gallery.R
import com.nasa_gallery.model.mars_data.MarsPhotosData
import com.nasa_gallery.model.app.AppStateMars
import com.nasa_gallery.data.retrofit.RepositoryImpl
import com.nasa_gallery.model.util.MyApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppStateMars> = MutableLiveData(),
    private val retrofitImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {
    private val contextVM = MyApp.appInstance!!.applicationContext
    fun getData(dateText: String, rover: String): LiveData<AppStateMars> {
        sendServerRequest(dateText, rover)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(dateText: String, rover: String) {
        liveDataForViewToObserve.value = AppStateMars.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppStateMars.Error(Throwable(contextVM.getString(R.string.Api_key_error)))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsPhotos(rover = rover, earth_date= dateText, apiKey=apiKey)
                .enqueue(object :
                    Callback<MarsPhotosData> {
                    override fun onResponse(
                        call: Call<MarsPhotosData>,
                        response: Response<MarsPhotosData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.postValue(AppStateMars.Success(response.body()!!))

                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {

                                liveDataForViewToObserve
                                    .postValue(
                                        AppStateMars.Error(
                                            Throwable(
                                                contextVM
                                                    .getString(R.string.error_message)
                                            )
                                        )
                                    )
                            } else {
                                liveDataForViewToObserve.postValue(AppStateMars.Error(Throwable(message)))
                            }
                        }
                    }

                    override fun onFailure(call: Call<MarsPhotosData>, t: Throwable) {
                        liveDataForViewToObserve.value = AppStateMars.Error(t)
                    }
                })
        }
    }
}
