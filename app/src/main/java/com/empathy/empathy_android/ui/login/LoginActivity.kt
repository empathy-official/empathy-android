package com.empathy.empathy_android.ui.login

import android.os.Bundle
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R


internal class LoginActivity: BaseActivity<LoginViewModel.ViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_login
    override fun getViewModel(): Class<LoginViewModel.ViewModel> = LoginViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}