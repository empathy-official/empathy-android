package com.empathy.empathy_android.ui.login

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class LoginActivity: BaseActivity<MapViewModel.ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }
}