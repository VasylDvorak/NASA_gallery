package com.nasa_gallery.ui.view.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nasa_gallery.BuildConfig
import com.nasa_gallery.R
import com.nasa_gallery.model.PictureOfTheDayResponseData
import com.nasa_gallery.model.app.AppState
import com.nasa_gallery.data.retrofit.RepositoryImpl
import com.nasa_gallery.model.util.MyApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {
    fun getData(dateText: String): LiveData<AppState> {
        sendServerRequest(dateText)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(dateText: String) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(MyApp.appInstance!!
                .applicationContext.getString(R.string.Api_key_error)))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDate(dateText, apiKey)
                .enqueue(object :
                    Callback<PictureOfTheDayResponseData> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayResponseData>,
                        response: Response<PictureOfTheDayResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.postValue(AppState.Success(response.body()!!))

                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {

                                liveDataForViewToObserve
                                    .postValue(
                                        AppState.Error(
                                            Throwable(
                                                MyApp.appInstance!!.applicationContext
                                                    .getString(R.string.error_message)
                                            )
                                        )
                                    )
                            } else {
                                liveDataForViewToObserve.postValue(AppState.Error(Throwable(message)))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                        liveDataForViewToObserve.value = AppState.Error(t)
                    }
                })
        }
    }
}
