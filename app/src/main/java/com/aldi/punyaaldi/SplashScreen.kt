package com.aldi.punyaaldi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldi.punyaaldi.slidepage.SlideScreen


class SplashScreen:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SlideScreen::class.java))
        finish()
    }
}

