package com.nasa_gallery.ui.ux


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

import com.nasa_gallery.R


class SplashActivity : AppCompatActivity() { //TODO HW single-activity пытаемся сохранить
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.IndigoTheme)
        setContentView(R.layout.activity_splash)
        findViewById<AppCompatImageView>(R.id.icon).animate().rotation(720f).setDuration(2000L).start()

     //   ObjectAnimator.ofFloat(findViewById<AppCompatImageView>(R.id.icon), View.ROTATION, 720f).setDuration(2000L).start()


        Handler(Looper.getMainLooper()).postDelayed({
    startActivity(Intent(this, UXActivity::class.java))
    finish()
},2000L)

    }
}