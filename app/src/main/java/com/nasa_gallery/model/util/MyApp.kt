package com.nasa_gallery.model.util

import android.app.Application

class MyApp : Application() {
    companion object {
        var appInstance: MyApp? = null}

    override fun onCreate() {
        super.onCreate()
      //  DynamicColors.applyToActivitiesIfAvailable(this)
        appInstance = this
    }
}