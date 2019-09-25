package com.aldi.punyaaldi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldi.punyaaldi.slidepage.PrefManager
import com.aldi.punyaaldi.slidepage.SlideScreen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ShowSlider.setOnClickListener {
            PrefManager(applicationContext).setLaunched(true)
            startActivity(Intent(this, SlideScreen::class.java))
            finish()
        }
    }
}