package com.nasa_gallery.ui.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa_gallery.BuildConfig
import com.nasa_gallery.MyApp
import com.nasa_gallery.R
import com.nasa_gallery.data.net.repository_nasa.RepositoryImpl
import com.nasa_gallery.data.net.model.PictureOfTheDayResponseData
import com.nasa_gallery.domain.application.AnswerFromServerStatePictureOfTheDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AnswerFromServerStatePictureOfTheDay>
    = MutableLiveData(), private val retrofitImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {
    fun getData(dateText: String): LiveData<AnswerFromServerStatePictureOfTheDay> {
        sendServerRequest(dateText)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(dateText: String) {
        liveDataForViewToObserve.value = AnswerFromServerStatePictureOfTheDay.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AnswerFromServerStatePictureOfTheDay.Error(
                Throwable(
                    MyApp.appInstance!!
                        .applicationContext.getString(R.string.Api_key_error)
                )
            )
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDate(dateText, apiKey)
                .enqueue(object :
                    Callback<PictureOfTheDayResponseData> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayResponseData>,
                        response: Response<PictureOfTheDayResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.postValue(
                                AnswerFromServerStatePictureOfTheDay.Success(
                                    response.body()!!
                                )
                            )

                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {

                                liveDataForViewToObserve
                                    .postValue(
                                        AnswerFromServerStatePictureOfTheDay.Error(
                                            Throwable(
                                                MyApp.appInstance!!.applicationContext
                                                    .getString(R.string.error_message)
                                            )
                                        )
                                    )
                            } else {
                                liveDataForViewToObserve.postValue(
                                    AnswerFromServerStatePictureOfTheDay.Error(Throwable(message))
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                        liveDataForViewToObserve.value =
                            AnswerFromServerStatePictureOfTheDay.Error(t)
                    }
                })
        }
    }
}
