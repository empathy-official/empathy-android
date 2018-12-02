package com.empathy.empathy_android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.login.LoginActivity


internal class SplashActivity: AppCompatActivity() {

    companion object {
        private const val DELAY_TIME = 2000.toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, DELAY_TIME)
    }
}