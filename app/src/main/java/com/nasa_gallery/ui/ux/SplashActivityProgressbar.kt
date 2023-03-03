package com.nasa_gallery.ui.ux


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.progressindicator.LinearProgressIndicator

import com.nasa_gallery.R


class SplashActivityProgressbar : AppCompatActivity() { //TODO HW single-activity пытаемся сохранить
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.IndigoTheme)
        setContentView(R.layout.activity_splash_progress_bar)
        val progress = findViewById<LinearProgressIndicator>(R.id.progress)

     //   ObjectAnimator.ofFloat(findViewById<AppCompatImageView>(R.id.icon), View.ROTATION, 720f).setDuration(2000L).start()

        val fullTime = 2000f
object:CountDownTimer(fullTime.toLong() , 5L){
    override fun onTick(p0: Long) {
        val process = ((1- p0/fullTime)*100).toInt()
        if( progress.progress !=process)
      progress.progress = process
    }

    override fun onFinish() {
        startActivity(Intent(this@SplashActivityProgressbar, UXActivity::class.java))
        finish()
    }
}.start()




    }
}