package com.nasa_gallery.util

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors

class MyApp : Application() {
    companion object {
        var appInstance: MyApp? = null}

    override fun onCreate() {
        super.onCreate()
      //  DynamicColors.applyToActivitiesIfAvailable(this)
        appInstance = this
    }
}