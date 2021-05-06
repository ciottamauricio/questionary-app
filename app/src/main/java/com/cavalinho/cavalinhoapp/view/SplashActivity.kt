package com.cavalinho.cavalinhoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.cavalinho.cavalinhoapp.R

class SplashActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, LoginActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
