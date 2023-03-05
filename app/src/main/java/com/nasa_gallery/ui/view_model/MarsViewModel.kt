package com.nasa_gallery.ui.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa_gallery.BuildConfig
import com.nasa_gallery.MyApp
import com.nasa_gallery.R
import com.nasa_gallery.data.net.RepositoryImpl
import com.nasa_gallery.data.net.model.mars_data.MarsPhotosData
import com.nasa_gallery.domain.application.AnswerFromServerStateMars
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AnswerFromServerStateMars> = MutableLiveData(),
    private val retrofitImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {
    private val contextVM = MyApp.appInstance!!.applicationContext
    fun getData(dateText: String, rover: String): LiveData<AnswerFromServerStateMars> {
        sendServerRequest(dateText, rover)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(dateText: String, rover: String) {
        liveDataForViewToObserve.value = AnswerFromServerStateMars.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AnswerFromServerStateMars.Error(Throwable(contextVM.getString(R.string.Api_key_error)))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsPhotos(
                rover = rover, earth_date = dateText,
                apiKey = apiKey
            )
                .enqueue(object :
                    Callback<MarsPhotosData> {
                    override fun onResponse(
                        call: Call<MarsPhotosData>,
                        response: Response<MarsPhotosData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.postValue(
                                AnswerFromServerStateMars.Success(
                                    response.body()!!
                                )
                            )

                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {

                                liveDataForViewToObserve
                                    .postValue(
                                        AnswerFromServerStateMars.Error(
                                            Throwable(
                                                contextVM
                                                    .getString(R.string.error_message)
                                            )
                                        )
                                    )
                            } else {
                                liveDataForViewToObserve.postValue(
                                    AnswerFromServerStateMars.Error(
                                        Throwable(message)
                                    )
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<MarsPhotosData>, t: Throwable) {
                        liveDataForViewToObserve.value = AnswerFromServerStateMars.Error(t)
                    }
                })
        }
    }
}
