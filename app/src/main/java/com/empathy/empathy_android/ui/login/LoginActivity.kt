package com.empathy.empathy_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.empathy.empathy_android.ui.feed.FeedActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


internal class LoginActivity: BaseActivity<LoginViewModel.ViewModel>() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun getLayoutRes(): Int = R.layout.activity_login
    override fun getViewModel(): Class<LoginViewModel.ViewModel> = LoginViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializePermission()
        initializeListener()
    }

    private fun initializePermission() {
        facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"))
    }

    private fun initializeListener() {
        facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                startActivity(Intent(this@LoginActivity, FeedActivity::class.java))
            }

            override fun onCancel() {
                Log.d("result", "onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("LoginErr", error.toString())
            }
        })

        facebook_login_view.setOnClickListener {
            if(AccessToken.getCurrentAccessToken() != null) {
                startActivity(Intent(this@LoginActivity, FeedActivity::class.java))

                return@setOnClickListener
            }

            facebook_login.performClick()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}