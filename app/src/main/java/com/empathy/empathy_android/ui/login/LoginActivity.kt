package com.empathy.empathy_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.empathy.empathy_android.BaseActivity
import com.empathy.empathy_android.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


internal class LoginActivity: BaseActivity<LoginViewModel.ViewModel>() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun getLayoutRes(): Int = R.layout.activity_login
    override fun getViewModel(): Class<LoginViewModel.ViewModel> = LoginViewModel.ViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        login_button.setReadPermissions(Arrays.asList("public_profile", "email"))
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val graphRequest = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                    Log.d("result", `object`.toString())
                }

                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,birthday")

                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }

            override fun onCancel() {
                Log.d("result", "onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("LoginErr", error.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}